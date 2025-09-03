package com.nsergio.dev.myrickandmortyapp.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.painter.Painter
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size

@Composable
fun RemoteAsyncImage(
    model: Any,
    size: Size = Size.ORIGINAL,
    crossfade: Boolean = false,
    onLoading: @Composable () -> Unit = {},
    onError: @Composable () -> Unit = {},
    onContentReady: @Composable (Painter) -> Unit
) {
    val platformContext = LocalPlatformContext.current

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(platformContext)
            .data(model)
            .size(size)
            .crossfade(crossfade)
            .build()
    )

    val state = painter.state.collectAsState().value

    when (state) {
        is AsyncImagePainter.State.Loading -> onLoading.invoke()
        is AsyncImagePainter.State.Success -> onContentReady.invoke(painter)
        is AsyncImagePainter.State.Error -> onError.invoke()
        else -> onError.invoke()
    }
}