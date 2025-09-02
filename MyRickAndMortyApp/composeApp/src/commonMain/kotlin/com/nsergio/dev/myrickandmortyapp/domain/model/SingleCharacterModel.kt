package com.nsergio.dev.myrickandmortyapp.domain.model

data class SingleCharacterModel(
    val id: Int,
    val name: String,
    val isAlive: Boolean,
    val gender: String,
    val image: String,
)