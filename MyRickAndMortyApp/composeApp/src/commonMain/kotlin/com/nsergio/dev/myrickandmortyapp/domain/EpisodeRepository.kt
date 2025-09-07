package com.nsergio.dev.myrickandmortyapp.domain

import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    suspend fun getSingleEpisode(id: Int): EpisodeModel

    suspend fun getEpisodesPager(): Flow<PagingData<EpisodeModel>>

}