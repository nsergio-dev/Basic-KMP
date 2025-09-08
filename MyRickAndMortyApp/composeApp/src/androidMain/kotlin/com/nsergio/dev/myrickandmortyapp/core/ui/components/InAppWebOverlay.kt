package com.nsergio.dev.myrickandmortyapp.core.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.os.Message
import android.webkit.CookieManager
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri

@Composable
fun InAppWebOverlay(
    visible: Boolean = true,
    url: String,
    onClose: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    val webViewHolder = rememberWebViewHolder()
    var title by remember { mutableStateOf("") }
    var progress by remember { mutableFloatStateOf(0f) }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(220)) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(220)) + fadeOut()
    ) {
        InAppWebScaffold(
            modifier = modifier,
            title = title.ifBlank { hostFrom(url) },
            progress = progress,
            onClose = {
                val wv = webViewHolder.value
                if (wv != null && wv.canGoBack()) {
                    wv.goBack()
                } else {
                    onClose()
                }
            }
        ) {
            WebViewContainer(
                webViewHolder = webViewHolder,
                url = url,
                onTitle = { title = it },
                onProgress = { progress = it }
            )
        }
    }

    BackHandler(enabled = visible) {
        val wv = webViewHolder.value
        if (wv != null && wv.canGoBack()) {
            wv.goBack()
        } else {
            onClose()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            webViewHolder.value?.apply {
                stopLoading()
                webChromeClient = null
                destroy()
            }
            webViewHolder.value = null
        }
    }

    LaunchedEffect(url, visible) {
        val wv = webViewHolder.value
        if (visible && wv != null && url.isNotBlank() && wv.url != url) {
            wv.loadUrl(url)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InAppWebScaffold(
    modifier: Modifier,
    title: String,
    progress: Float,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClose) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier.statusBarsPadding()
            )
            if (progress in 0f..0.99f) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.Transparent
                )
            }
        }
    }
}

@Composable
private fun WebViewContainer(
    webViewHolder: MutableState<WebView?>,
    url: String,
    onTitle: (String) -> Unit,
    onProgress: (Float) -> Unit
) {
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            createWebView(
                context = context,
                onTitle = onTitle,
                onProgress = onProgress
            ).also { wv ->
                webViewHolder.value = wv
                wv.loadUrl(url)
            }
        },
        update = { wv ->
            val current = wv.url.orEmpty()
            if (current != url && url.isNotBlank()) {
                wv.loadUrl(url)
            }
        }
    )
}

@Composable
private fun rememberWebViewHolder(): MutableState<WebView?> {
    return remember { mutableStateOf(null) }
}

private fun hostFrom(raw: String): String {
    return runCatching { raw.toUri().host.orEmpty() }.getOrDefault("")
}

private fun createWebView(
    context: Context,
    onTitle: (String) -> Unit,
    onProgress: (Float) -> Unit
): WebView {
    return WebView(context).apply {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.loadsImagesAutomatically = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        settings.setSupportZoom(true)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.userAgentString = settings.userAgentString + " InstagramLikeWebView/1.0"
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        webViewClient = createWebViewClient(onTitle, onProgress)
        webChromeClient = createWebChromeClient(onProgress)
    }
}

private fun createWebViewClient(
    onTitle: (String) -> Unit,
    onProgress: (Float) -> Unit
): WebViewClient {
    return object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request == null) return false
            val scheme = request.url?.scheme.orEmpty().lowercase()
            return !(scheme == "http" || scheme == "https")
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            onProgress(0.05f)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            onTitle(view?.title.orEmpty())
            onProgress(1f)
        }
    }
}

private fun createWebChromeClient(
    onProgress: (Float) -> Unit
): WebChromeClient {
    return object : WebChromeClient() {
        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val data = view?.hitTestResult?.extra
            if (!data.isNullOrEmpty()) {
                view.loadUrl(data)
            }
            return false
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            onProgress((newProgress.coerceIn(0, 100)) / 100f)
        }

        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            result?.confirm()
            return true
        }
    }
}