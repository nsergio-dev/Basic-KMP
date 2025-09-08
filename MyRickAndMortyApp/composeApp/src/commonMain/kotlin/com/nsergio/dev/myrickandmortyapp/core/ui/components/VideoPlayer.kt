package com.nsergio.dev.myrickandmortyapp.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun VideoPlayer(modifier: Modifier, videoUrl: String)