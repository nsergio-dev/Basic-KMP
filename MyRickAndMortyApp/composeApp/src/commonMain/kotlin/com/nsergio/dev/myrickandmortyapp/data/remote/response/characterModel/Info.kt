package com.nsergio.dev.myrickandmortyapp.data.remote.response.characterModel

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val count: Int? = 0,
    val pages: Int,
    val next: String?,
    val prev: String?
)