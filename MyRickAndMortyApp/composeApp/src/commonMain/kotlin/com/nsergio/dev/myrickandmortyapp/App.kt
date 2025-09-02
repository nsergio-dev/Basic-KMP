package com.nsergio.dev.myrickandmortyapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.nsergio.dev.myrickandmortyapp.core.navigation.NavWrapper
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavWrapper()
    }
}