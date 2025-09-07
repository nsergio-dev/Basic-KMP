package com.nsergio.dev.myrickandmortyapp.data.local.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nsergio.dev.myrickandmortyapp.domain.model.CharacterOfTheDayModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel


@Entity(tableName = "characters")
data class CharacterEntity(

    @PrimaryKey val id: Int,
    val status: String,
    val name: String,
    val gender: String,
    val image: String,
    val selectedDay: String
) {
    fun toDomain(): CharacterOfTheDayModel {
        val singleCharacterModel = SingleCharacterModel(
            id = id, name = name, isAlive = status == "Alive", gender = gender, image = image
        )
        val characterOfTheDayModel = CharacterOfTheDayModel(
            model = singleCharacterModel, selectedDay = selectedDay
        )
        return characterOfTheDayModel
    }

}
