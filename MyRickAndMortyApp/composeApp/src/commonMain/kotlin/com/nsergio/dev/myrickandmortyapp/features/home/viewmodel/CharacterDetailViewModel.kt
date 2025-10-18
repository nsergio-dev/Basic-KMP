package com.nsergio.dev.myrickandmortyapp.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import com.nsergio.dev.myrickandmortyapp.domain.usecase.episodes.GetEpisodeUseCase
import com.nsergio.dev.myrickandmortyapp.domain.usecase.episodes.GetEpisodesUseCase
import com.nsergio.dev.myrickandmortyapp.features.detail.statemodels.CharacterDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterModel: SingleCharacterModel,
    private val episodesUseCase: GetEpisodesUseCase,
    private val getEpisodeUseCase: GetEpisodeUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CharacterDetailUiState> = MutableStateFlow(
        CharacterDetailUiState(character = characterModel)
    )

    val uiState: StateFlow<CharacterDetailUiState> = _uiState

    fun getEpisodes(episodesId: List<String>) {
        viewModelScope.launch {
            val episodes = if (episodesId.isEmpty()) {
                emptyList()
            } else {
                if (episodesId.count() > 1) {
                    val episodes = episodesUseCase.invoke(episodesId)
                    episodes
                } else {
                    val episode = getEpisodeUseCase.invoke(episodesId.first())
                    listOf(episode)
                }
            }

            _uiState.update { state ->
                state.copy(characterEpisodes = episodes)
            }

        }
    }

}