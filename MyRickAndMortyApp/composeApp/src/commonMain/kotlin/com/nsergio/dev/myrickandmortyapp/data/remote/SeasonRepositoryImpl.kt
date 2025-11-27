package com.nsergio.dev.myrickandmortyapp.data.remote

import com.nsergio.dev.myrickandmortyapp.domain.SeasonRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.SeasonModel

class SeasonRepositoryImpl(
    private val apiService: ApiService
) : SeasonRepository {

    override suspend fun getAllSeason(): List<SeasonModel> {
        val response = apiService.getAllEpisodes()
        val models = response.map { it.toDomain() }
        val seasons = models.groupBy { it.season }

        val seas = mutableListOf<SeasonModel>()
        seasons.forEach { (season, episodes) ->
            seas.add(SeasonModel(season, episodes))
        }

        return seasons.map { (season, episodes) ->
            SeasonModel(season, episodes)
        }
    }
}