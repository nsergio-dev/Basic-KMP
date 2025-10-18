package com.nsergio.dev.myrickandmortyapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.data.remote.paging.PagingEpisodesSource
import com.nsergio.dev.myrickandmortyapp.domain.EpisodeRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import kotlinx.coroutines.flow.Flow

class EpisodeRepositoryImpl(
    private val apiService: ApiService,
    private val episodesSource: PagingEpisodesSource,
) : EpisodeRepository {

    override suspend fun getSingleEpisode(id: String): EpisodeModel {
        val response = apiService.getSingleEpisode(id)
        val model = response.toDomain()
        return model

    }

    override suspend fun getEpisodeFromAPI(id: String): EpisodeModel {
        val response = apiService.getSingleEpisode(id)
        val model = response.toDomain()
        return model
    }

    override suspend fun getEpisodesFromAPI(episodesId: List<String>): List<EpisodeModel> {
        val response = apiService.getEpisodes(episodesId)
        val models = response.map { it.toDomain() }
        return models
    }

    override suspend fun getEpisodesPager(): Flow<PagingData<EpisodeModel>> {

        val pagerConfig = PagingConfig(
            pageSize = ITEMS_PER_PAGE,
            prefetchDistance = ITEMS_TO_LOAD_NEW_ITEMS
        )

        val pager = Pager(
            config = pagerConfig,
            pagingSourceFactory = { episodesSource }
        ).flow

        return pager
    }

    companion object Companion {
        const val ITEMS_PER_PAGE = 10
        const val ITEMS_TO_LOAD_NEW_ITEMS = 4
    }

}