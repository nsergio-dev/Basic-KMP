package com.nsergio.dev.myrickandmortyapp.domain.model

data class SeasonModel(
    val seasonEpisode: SeasonEpisode,
    val episodes: List<EpisodeModel>
)