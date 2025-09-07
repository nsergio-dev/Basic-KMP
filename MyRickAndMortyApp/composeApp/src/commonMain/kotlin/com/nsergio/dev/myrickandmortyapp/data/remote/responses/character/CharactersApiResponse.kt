package com.nsergio.dev.myrickandmortyapp.data.remote.responses.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersApiResponse(

    val info: Info,
    @SerialName("results")
    val characters: List<SingleCharacterResponse> = emptyList()
)