package com.nsergio.dev.myrickandmortyapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform