package com.nsergio.dev.myrickandmortyapp.data.remote.response

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

)