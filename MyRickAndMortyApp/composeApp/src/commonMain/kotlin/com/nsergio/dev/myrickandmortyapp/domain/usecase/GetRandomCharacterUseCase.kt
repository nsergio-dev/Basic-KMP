package com.nsergio.dev.myrickandmortyapp.domain.usecase

import com.nsergio.dev.myrickandmortyapp.domain.Repository
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GetRandomCharacterUseCase(private val repository: Repository) {

    suspend operator fun invoke(): SingleCharacterModel {
        val currentDay = getCurrentDayOfTheYear()
        val caracterFromDB = repository.getCharacterFromDB()
        /*if (currentDay == caracterFromDB.date) {

        }*/
        caracterFromDB
        val maxCharacters = 826
        val idCharacter = (1..maxCharacters).random()
        return repository.getSingleCharacter(id = idCharacter)
    }

    private fun getCurrentDayOfTheYear(): String {

        val instant: Instant = Clock.System.now()
        val localTime = instant.toLocalDateTime(
            TimeZone.currentSystemDefault()
        )

        val date = "${localTime.dayOfYear} - ${localTime.year}"
        return  date
    }
}