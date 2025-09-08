package com.nsergio.dev.myrickandmortyapp.core.ui.components

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import java.util.Locale
import androidx.core.net.toUri

//there options to showVideo in android:
@Composable
actual fun VideoPlayer(modifier: Modifier, videoUrl: String) {
    CustomTabsSmartVideoPlayer(url = videoUrl)
    /*InAppWebOverlay(
        url = videoUrl,
        modifier = modifier
    )*/
    /*SmartVideoPlayer(
        url = videoUrl,
        modifier = modifier
    )*/
    /*AndroidView(
        modifier = modifier,
        factory = { context ->
            val videoView = VideoView(context)
            val mediaController = MediaController(context)
            videoView.apply {
                //dummy video, fails to load, YoutubeVideos on Android
                setVideoPath("https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                mediaController.setAnchorView(videoView)
                start()
            }
        }
    )*/
}

@Composable
fun CustomTabsSmartVideoPlayer(url: String) {
    val context = LocalContext.current

    LaunchedEffect(url) {
        val provider = CustomTabsClient.getPackageName(context, null) ?: "com.android.chrome"
        val customTabs = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)
            .setToolbarColor(Color.Black.value.toInt())
            .build()
        customTabs.intent.setPackage(provider)

        runCatching {
            customTabs.launchUrl(context, url.toUri())
        }.onFailure {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    }
}

@Composable
fun SmartVideoPlayer(
    url: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    loop: Boolean = false,
    muteForAutoplay: Boolean = true
) {
    val currentUrl by remember(url) { mutableStateOf(url) }
    val isYouTube = remember(currentUrl) { isYouTubeUrl(currentUrl) }

    if (isYouTube) {
        val videoId = extractYouTubeId(currentUrl) ?: return
        YouTubeEmbeddedWebView(
            videoId = videoId,
            modifier = modifier,
            autoPlay = autoPlay,
            mute = muteForAutoplay
        )
    } else {
        ExoPlayerView(
            url = currentUrl,
            modifier = modifier,
            autoPlay = autoPlay,
            loop = loop
        )
    }
}

@Composable
private fun YouTubeEmbeddedWebView(
    videoId: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    mute: Boolean = true
) {
    val embedUrl = remember(videoId, autoPlay, mute) {
        buildYouTubeEmbedUrl(videoId = videoId, autoPlay = autoPlay, mute = mute)
    }

    AndroidView(
        modifier = modifier,
        update = { webView ->
            if (webView.url != embedUrl) {
                webView.loadUrl(embedUrl)
            }
        },
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
                settings.mediaPlaybackRequiresUserGesture = !(autoPlay && mute)
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                webChromeClient = WebChromeClient()
                webViewClient = WebViewClient()

                loadUrl(embedUrl)
            }
        },
        onRelease = { webView ->
            webView.apply {
                stopLoading()
                loadUrl("about:blank")
                clearHistory()
                clearCache(true)
                removeAllViews()
                webChromeClient = WebChromeClient()
                webViewClient = WebViewClient()
                destroy()
            }
        }
    )
}

private fun buildYouTubeEmbedUrl(
    videoId: String,
    autoPlay: Boolean,
    mute: Boolean
): String {
    val ap = if (autoPlay) "1" else "0"
    val m = if (mute) "1" else "0"

    return "https://www.youtube.com/embed/$videoId?autoplay=$ap&mute=$m&controls=1&modestbranding=1&rel=0&playsinline=1"
}

@Composable
private fun ExoPlayerView(
    url: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    loop: Boolean = false
) {
    val context = LocalContext.current
    val exoPlayer = remember(url, autoPlay, loop) {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            setMediaItem(mediaItem)
            repeatMode = if (loop) ExoPlayer.REPEAT_MODE_ONE else ExoPlayer.REPEAT_MODE_OFF
            prepare()
            playWhenReady = autoPlay
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = true
            }
        },
        onRelease = { view -> view.player = null }
    )

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }
}

private fun isYouTubeUrl(url: String): Boolean {
    val host = runCatching { Uri.parse(url).host?.lowercase(Locale.US) ?: "" }.getOrNull().orEmpty()
    return host.contains("youtube.com") || host.contains("youtu.be")
}

private fun extractYouTubeId(url: String): String? {
    val uri = Uri.parse(url)
    val host = uri.host?.lowercase(Locale.US).orEmpty()
    return when {
        host.contains("youtu.be") -> uri.lastPathSegment
        host.contains("youtube.com") -> {
            uri.getQueryParameter("v")
                ?: uri.pathSegments.let { segs ->
                    val keys = listOf("embed", "shorts")
                    val idx = segs.indexOfFirst { it in keys }
                    if (idx >= 0 && segs.size > idx + 1) segs[idx + 1] else null
                }
        }

        else -> null
    }
}