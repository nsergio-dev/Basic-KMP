package com.nsergio.dev.myrickandmortyapp.domain

import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    suspend fun getSingleEpisode(id: String): EpisodeModel

    suspend fun getEpisodeFromAPI(id: String): EpisodeModel

    suspend fun getEpisodesFromAPI(episodesId: List<String>): List<EpisodeModel>

    suspend fun getEpisodesPager(): Flow<PagingData<EpisodeModel>>

}