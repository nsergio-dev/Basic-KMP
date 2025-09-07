package com.nsergio.dev.myrickandmortyapp.domain.usecase.episodes

import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.domain.EpisodeRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import kotlinx.coroutines.flow.Flow

class GetEpisodesPagerUserCase(
    private val repository: EpisodeRepository
) {

    suspend operator fun invoke(): Flow<PagingData<EpisodeModel>> {
        val pager = repository.getEpisodesPager()
        return pager
    }

}