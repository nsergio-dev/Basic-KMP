package com.nsergio.dev.myrickandmortyapp.data.remote.responses.character

import com.nsergio.dev.myrickandmortyapp.domain.model.LocationCharacterModel
import kotlinx.serialization.Serializable

@Serializable
data class LocationCharacterModelResponse(
    val name: String = "",
    val url: String = ""
) {

    fun toDomain(): LocationCharacterModel {
        return LocationCharacterModel(
            name = name,
            url = url
        )
    }

}