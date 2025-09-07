package com.nsergio.dev.myrickandmortyapp.domain.model

import com.nsergio.dev.myrickandmortyapp.data.local.entitites.CharacterEntity

data class SingleCharacterModel(
    val id: Int,
    val name: String,
    val isAlive: Boolean,
    val gender: String,
    val image: String,
) {

    fun toEntity(date: String = ""): CharacterEntity {
        return CharacterEntity(
            id = id,
            name = name,
            status = if (isAlive) "Alive" else "Dead",
            gender = gender,
            image = image,
            selectedDay = date
        )

    }
}