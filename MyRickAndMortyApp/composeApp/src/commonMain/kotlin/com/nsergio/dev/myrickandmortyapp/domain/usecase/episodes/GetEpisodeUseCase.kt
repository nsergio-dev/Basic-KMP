package com.nsergio.dev.myrickandmortyapp.domain.usecase.episodes

import com.nsergio.dev.myrickandmortyapp.domain.EpisodeRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel

class GetEpisodeUseCase(
    private val repository: EpisodeRepository
) {

    suspend operator fun invoke(episodeId: String): EpisodeModel {
        val episode = repository.getEpisodeFromAPI(episodeId)
        return episode
    }

}