package com.nsergio.dev.myrickandmortyapp.domain.model

import com.nsergio.dev.myrickandmortyapp.data.local.entitites.CharacterEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SingleCharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val specie: String,
    val gender: String,
    val origin: OriginCharacterModel,
    val location: LocationCharacterModel,
    val image: String,
    @SerialName("episode")
    val episodes: List<String>,
    val url: String,
    val created: String
) {

    fun toEntity(date: String = ""): CharacterEntity {
        return CharacterEntity(
            id = id,
            name = name,
            status = status,
            gender = gender,
            image = image,
            specie = specie,
            origin = Json.encodeToString(origin),
            location = Json.encodeToString(location),
            url = url,
            created = created,
            selectedDay = date
        )

    }
}