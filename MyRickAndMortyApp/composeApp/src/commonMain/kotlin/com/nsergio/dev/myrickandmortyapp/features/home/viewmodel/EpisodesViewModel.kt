package com.nsergio.dev.myrickandmortyapp.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsergio.dev.myrickandmortyapp.domain.usecase.season.GetAllSeasonsUseCase
import com.nsergio.dev.myrickandmortyapp.features.home.statemodels.EpisodesUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EpisodesViewModel(
    private val getAllSeasonsUseCase: GetAllSeasonsUseCase
) : ViewModel() {

    private val _videoUrl = MutableStateFlow("")
    val videoUrl: StateFlow<String> = _videoUrl

    private val _state: MutableStateFlow<EpisodesUIState> = MutableStateFlow(EpisodesUIState())
    val state: StateFlow<EpisodesUIState> = _state

    fun loadSeasons() {
        viewModelScope.launch(Dispatchers.IO) {
            val seasons = getAllSeasonsUseCase.invoke()
            _state.update { currentState ->
                currentState.copy(seasons = seasons)
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