package com.nsergio.dev.myrickandmortyapp.data.remote.responses.character

import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleCharacterResponse(

    @SerialName("id")
    val idCharacter: Int,
    val status: String,
    val name: String,
    val gender: String,
    val image: String

) {
    fun toDomain(): SingleCharacterModel {
        return SingleCharacterModel(
            id = idCharacter,
            name = name,
            isAlive = status.equals("alive", ignoreCase = true),
            gender = gender,
            image = image
        )
    }
}