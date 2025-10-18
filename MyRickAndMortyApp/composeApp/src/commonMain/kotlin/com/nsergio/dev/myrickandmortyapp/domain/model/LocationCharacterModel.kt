package com.nsergio.dev.myrickandmortyapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationCharacterModel(
    val name: String = "",
    val url: String = ""
)