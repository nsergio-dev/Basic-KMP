package com.nsergio.dev.myrickandmortyapp.domain.usecase.character

import com.nsergio.dev.myrickandmortyapp.domain.CharacterRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.CharacterOfTheDayModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.getRandomDelay
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GetRandomCharacterUseCase(private val repository: CharacterRepository) {

    suspend operator fun invoke(): SingleCharacterModel {
        val currentDay = getCurrentDayOfTheYear()
        val characterFromDB: CharacterOfTheDayModel? = repository.getCharacterFromDB()
        if (characterFromDB != null && characterFromDB.selectedDay == currentDay) {
            val model = characterFromDB.model
            return model
        } else {
            val characterModel = getRandomCharacterFromRemote()
            repository.saveCharacterOfTheDay(characterModel, currentDay)
            delay(getRandomDelay())
            return characterModel
        }
    }

    private suspend fun getRandomCharacterFromRemote(): SingleCharacterModel {
        val maxCharacters = 826
        val idCharacter = (1..maxCharacters).random()
        val character = repository.getSingleCharacter(id = idCharacter)
        return character
    }

    private fun getCurrentDayOfTheYear(): String {

        val instant: Instant = Clock.System.now()
        val localTime = instant.toLocalDateTime(
            TimeZone.currentSystemDefault()
        )

        val date = "${localTime.dayOfYear} - ${localTime.year}"
        return date
    }
}