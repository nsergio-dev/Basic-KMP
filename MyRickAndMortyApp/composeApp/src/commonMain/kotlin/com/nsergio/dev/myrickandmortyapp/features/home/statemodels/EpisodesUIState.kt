package com.nsergio.dev.myrickandmortyapp.features.home.statemodels

import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class EpisodesUIState(

    val charactersPagingData: Flow<PagingData<EpisodeModel>> = emptyFlow()
)