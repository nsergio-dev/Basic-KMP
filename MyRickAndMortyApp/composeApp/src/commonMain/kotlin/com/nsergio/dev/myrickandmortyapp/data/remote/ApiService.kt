package com.nsergio.dev.myrickandmortyapp.data.remote

import com.nsergio.dev.myrickandmortyapp.data.remote.responses.character.CharactersApiResponse
import com.nsergio.dev.myrickandmortyapp.data.remote.responses.character.SingleCharacterResponse
import com.nsergio.dev.myrickandmortyapp.data.remote.responses.episodes.EpisodeItemResponse
import com.nsergio.dev.myrickandmortyapp.data.remote.responses.episodes.EpisodeResponse
import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.getRandomDelay
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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

        return withContext(Dispatchers.IO) {
            //dummy delay to simulate lazy network call
            delay(getRandomDelay())
            response
        }
    }

    suspend fun getSingleEpisode(id: String): EpisodeItemResponse {
        return withContext(Dispatchers.IO) {
            val request = client.get(urlString = "/api/episode/$id")
            val response = request.body<EpisodeItemResponse>()
            delay(getRandomDelay())
            response
        }
    }

    suspend fun getAllEpisodes(page: Int): EpisodeResponse {

        return withContext(Dispatchers.IO) {

            val request = client.get(urlString = "/api/episode") {
                parameter("page", page)
            }

            val response = request.body<EpisodeResponse>()

            delay(getRandomDelay())

            response
        }
    }

    suspend fun getEpisodes(episodesId: List<String>): List<EpisodeItemResponse> {

        return withContext(Dispatchers.IO) {

            val episodes = episodesId.joinToString(",")

            val request = client.get(urlString = "api/episode/$episodes")

            val response = request.body<List<EpisodeItemResponse>>()

            response
        }

    }

}