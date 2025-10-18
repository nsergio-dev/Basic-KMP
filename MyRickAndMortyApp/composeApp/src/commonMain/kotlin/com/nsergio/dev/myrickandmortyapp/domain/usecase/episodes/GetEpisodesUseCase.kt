package com.nsergio.dev.myrickandmortyapp.domain.usecase.episodes

import com.nsergio.dev.myrickandmortyapp.domain.EpisodeRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel

class GetEpisodesUseCase(private val repository: EpisodeRepository) {

    suspend operator fun invoke(episodesId: List<String>): List<EpisodeModel> {
        val episodes = repository.getEpisodesFromAPI(episodesId)
        return episodes
    }

}