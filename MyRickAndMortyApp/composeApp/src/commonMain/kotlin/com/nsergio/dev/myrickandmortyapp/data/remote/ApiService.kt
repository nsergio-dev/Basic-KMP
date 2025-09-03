package com.nsergio.dev.myrickandmortyapp.data.remote

import com.nsergio.dev.myrickandmortyapp.data.remote.response.CharactersApiResponse
import com.nsergio.dev.myrickandmortyapp.data.remote.response.SingleCharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val client: HttpClient) {

    suspend fun getSingleCharacter(id: Int): SingleCharacterResponse {

        val request = client.get(urlString = "/api/character/$id")
        val response = request.body<SingleCharacterResponse>()

        return response
    }

    suspend fun getCharacters(page: Int): CharactersApiResponse {

        val request = client.get(urlString = "/api/character") {
            parameter("page", page)
        }

        val response = request.body<CharactersApiResponse>()

        return response
    }
}