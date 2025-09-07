package com.nsergio.dev.myrickandmortyapp.domain.usecase.character

import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.domain.CharacterRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(private val repository: CharacterRepository) {

    suspend operator fun invoke(): Flow<PagingData<SingleCharacterModel>> {
        return repository.getCharacters()
    }

}