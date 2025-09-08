package com.nsergio.dev.myrickandmortyapp.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nsergio.dev.myrickandmortyapp.domain.usecase.episodes.GetEpisodesPagerUserCase
import com.nsergio.dev.myrickandmortyapp.features.home.statemodels.EpisodesUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EpisodesViewModel(
    private val episodesPager: GetEpisodesPagerUserCase
) : ViewModel() {

    private val _videoUrl = MutableStateFlow("")
    val videoUrl: StateFlow<String> = _videoUrl

    private val _state: MutableStateFlow<EpisodesUIState> = MutableStateFlow(EpisodesUIState())
    val state: StateFlow<EpisodesUIState> = _state

    fun loadEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            val pager = episodesPager.invoke().cachedIn(viewModelScope)
            _state.update { currentState ->
                currentState.copy(charactersPagingData = pager)
            }
        }
    }

    fun openVideo(url: String) {
        _videoUrl.value = url
        viewModelScope.launch(Dispatchers.IO) {
            delay(2_000L)
            //to force recomposition
            _videoUrl.value = ""
        }

    }

}