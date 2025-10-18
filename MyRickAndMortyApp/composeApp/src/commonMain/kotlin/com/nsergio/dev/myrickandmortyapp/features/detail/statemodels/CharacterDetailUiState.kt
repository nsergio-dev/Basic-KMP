package com.nsergio.dev.myrickandmortyapp.features.detail.statemodels

import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel

data class CharacterDetailUiState(
    val character: SingleCharacterModel? = null,
    val characterEpisodes: List<EpisodeModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
