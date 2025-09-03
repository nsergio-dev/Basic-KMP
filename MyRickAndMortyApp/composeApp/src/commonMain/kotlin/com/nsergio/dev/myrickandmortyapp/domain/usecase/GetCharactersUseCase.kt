package com.nsergio.dev.myrickandmortyapp.domain.usecase

import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.domain.Repository
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(private val repository: Repository) {

    suspend operator fun invoke(): Flow<PagingData<SingleCharacterModel>> {
        return repository.getCharacters()
    }

}