package com.nsergio.dev.myrickandmortyapp.data.remote

import com.nsergio.dev.myrickandmortyapp.domain.Repository
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel

class RepositoryImpl(val apiService: ApiService) : Repository {

    override suspend fun getSingleCharacter(id: Int) {
        val apiResponse = apiService.getSingleCharacter(id)
        print(apiResponse)

    }

}

