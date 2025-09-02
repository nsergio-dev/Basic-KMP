package com.nsergio.dev.myrickandmortyapp.features.home.screens

import android.os.Build

actual fun helloName(): String {
    return "Hello from Android \nVersion ${Build.VERSION.SDK_INT} - ${Build.VERSION.RELEASE} }"
}