package com.nsergio.dev.myrickandmortyapp.di

import com.nsergio.dev.myrickandmortyapp.data.remote.ApiService
import com.nsergio.dev.myrickandmortyapp.data.remote.CharacterRepositoryImpl
import com.nsergio.dev.myrickandmortyapp.data.remote.EpisodeRepositoryImpl
import com.nsergio.dev.myrickandmortyapp.data.remote.paging.PagingCharactersSource
import com.nsergio.dev.myrickandmortyapp.data.remote.paging.PagingEpisodesSource
import com.nsergio.dev.myrickandmortyapp.domain.CharacterRepository
import com.nsergio.dev.myrickandmortyapp.domain.EpisodeRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

// module for di with data 'scope'

val dataModule = module {
    single {

        HttpClient {
            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true },
                    contentType = ContentType.Any
                )
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "rickandmortyapi.com"
                    // if you need to send additional parameters
                    //parameters.append("api_key", "some_api_key")
                }
            }
        }
    }

    factoryOf(::ApiService)
    factoryOf(::PagingCharactersSource)
    factoryOf(::PagingEpisodesSource)
    factory<CharacterRepository> {
        CharacterRepositoryImpl(
            apiService = get(),
            pagingSource = get(),
            database = get()
        )
    }
    factory<EpisodeRepository> {
        EpisodeRepositoryImpl(
            apiService = get(),
            episodesSource = get()
        )
    }

}