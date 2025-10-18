package com.nsergio.dev.myrickandmortyapp.data.remote.responses.character

import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleCharacterResponse(

    @SerialName("id")
    val idCharacter: Int,
    val name: String,
    val status: String,
    @SerialName("species")
    val specie: String,
    val gender: String,
    val origin: OriginCharacterModelResponse,
    val location: LocationCharacterModelResponse,
    val image: String,
    @SerialName("episode")
    val episodes: List<String>,
    val url: String,
    val created: String

) {
    fun toDomain(): SingleCharacterModel {
        return SingleCharacterModel(
            id = idCharacter,
            name = name,
            status = status,
            specie = specie,
            gender = gender,
            origin = origin.toDomain(),
            location = location.toDomain(),
            image = image,
            episodes = episodes.map { it.substringAfterLast("/") },
            url = url,
            created = created
        )
    }
}