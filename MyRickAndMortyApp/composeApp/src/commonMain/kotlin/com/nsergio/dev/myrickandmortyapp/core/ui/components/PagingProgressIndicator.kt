package com.nsergio.dev.myrickandmortyapp.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green

@Composable
fun PagingProgressBarIndicator(
    modifier: Modifier,
    colorIndicator: Color = Green,
    contentAlignment: Alignment = Alignment.Center
) {

    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
    ) {
        CircularProgressIndicator(color = colorIndicator)
    }

}