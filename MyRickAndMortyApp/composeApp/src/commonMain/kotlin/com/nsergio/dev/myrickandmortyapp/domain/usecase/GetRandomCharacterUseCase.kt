package com.nsergio.dev.myrickandmortyapp.domain.usecase

import com.nsergio.dev.myrickandmortyapp.domain.Repository
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel

class GetRandomCharacterUseCase (private val repository: Repository) {

    suspend operator fun invoke(): SingleCharacterModel {
        val maxCharacters = 826
        val idCharacter = (1..maxCharacters).random()
        return repository.getSingleCharacter(id = idCharacter)
    }



}