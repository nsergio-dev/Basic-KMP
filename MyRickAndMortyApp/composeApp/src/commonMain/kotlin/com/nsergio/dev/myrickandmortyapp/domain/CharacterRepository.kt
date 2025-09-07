package com.nsergio.dev.myrickandmortyapp.domain

import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.domain.model.CharacterOfTheDayModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getSingleCharacter(id: Int): SingleCharacterModel

    suspend fun getCharacters(): Flow<PagingData<SingleCharacterModel>>

    suspend fun getCharacterFromDB(): CharacterOfTheDayModel?

    suspend fun saveCharacterOfTheDay(characterModel: SingleCharacterModel, currentDay: String)

}