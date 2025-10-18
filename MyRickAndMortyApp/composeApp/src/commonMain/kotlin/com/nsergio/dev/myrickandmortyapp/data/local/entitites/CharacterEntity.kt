package com.nsergio.dev.myrickandmortyapp.data.local.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nsergio.dev.myrickandmortyapp.domain.model.CharacterOfTheDayModel
import com.nsergio.dev.myrickandmortyapp.domain.model.LocationCharacterModel
import com.nsergio.dev.myrickandmortyapp.domain.model.OriginCharacterModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.serialization.json.Json

@Entity(tableName = "characters")
data class CharacterEntity(

    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val specie: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
    val url: String,
    val created: String,
    val selectedDay: String
) {
    fun toDomain(): CharacterOfTheDayModel {
        val singleCharacterModel = SingleCharacterModel(
            id = id,
            name = name,
            status = status,
            specie = specie,
            gender = gender,
            origin = Json.decodeFromString(origin) ?: OriginCharacterModel(),
            location = Json.decodeFromString(location) ?: LocationCharacterModel(),
            image = image,
            episodes = emptyList(),
            url = url,
            created = created
        )
        val characterOfTheDayModel = CharacterOfTheDayModel(
            model = singleCharacterModel,
            selectedDay = selectedDay
        )
        return characterOfTheDayModel
    }

}
