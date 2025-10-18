package com.nsergio.dev.myrickandmortyapp.data.remote.responses.character

import com.nsergio.dev.myrickandmortyapp.domain.model.OriginCharacterModel
import kotlinx.serialization.Serializable

@Serializable
data class OriginCharacterModelResponse(
    val name: String = "",
    val url: String = ""
) {
    fun toDomain(): OriginCharacterModel {
        return OriginCharacterModel(
            name = name,
            url = url
        )
    }
}