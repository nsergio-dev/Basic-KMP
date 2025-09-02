package com.nsergio.dev.myrickandmortyapp

import androidx.compose.ui.window.ComposeUIViewController
import com.nsergio.dev.myrickandmortyapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    },
    content = {
        App()
    }
)