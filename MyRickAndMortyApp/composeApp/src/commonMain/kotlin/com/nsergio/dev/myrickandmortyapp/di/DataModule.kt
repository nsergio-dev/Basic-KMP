package com.nsergio.dev.myrickandmortyapp.di

import com.nsergio.dev.myrickandmortyapp.data.remote.ApiService
import com.nsergio.dev.myrickandmortyapp.data.remote.RepositoryImpl
import com.nsergio.dev.myrickandmortyapp.domain.Repository
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

    factory<Repository> { RepositoryImpl(get()) }
}