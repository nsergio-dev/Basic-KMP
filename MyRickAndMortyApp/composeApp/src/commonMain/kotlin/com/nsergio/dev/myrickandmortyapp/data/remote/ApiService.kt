package com.nsergio.dev.myrickandmortyapp.data.remote

import com.nsergio.dev.myrickandmortyapp.data.remote.response.SingleCharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val client: HttpClient) {

    suspend fun getSingleCharacter(id: Int): SingleCharacterResponse {

        val request = client.get(urlString = "/api/character/$id")
        val response = request.body<SingleCharacterResponse>()

        return response
    }
}