package com.nsergio.dev.myrickandmortyapp.data.remote.response.characterModel

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Int = 0
)