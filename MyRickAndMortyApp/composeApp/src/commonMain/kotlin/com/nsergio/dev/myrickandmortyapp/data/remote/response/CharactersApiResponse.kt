package com.nsergio.dev.myrickandmortyapp.data.remote.response

import com.nsergio.dev.myrickandmortyapp.data.remote.response.characterModel.Info
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersApiResponse(

    val info: Info,
    @SerialName("results")
    val characters: List<SingleCharacterResponse> = emptyList()
)