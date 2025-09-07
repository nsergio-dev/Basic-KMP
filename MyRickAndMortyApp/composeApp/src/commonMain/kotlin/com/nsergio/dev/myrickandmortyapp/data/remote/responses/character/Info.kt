package com.nsergio.dev.myrickandmortyapp.data.remote.responses.character

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val count: Int? = 0,
    val pages: Int,
    val next: String?,
    val prev: String?
)