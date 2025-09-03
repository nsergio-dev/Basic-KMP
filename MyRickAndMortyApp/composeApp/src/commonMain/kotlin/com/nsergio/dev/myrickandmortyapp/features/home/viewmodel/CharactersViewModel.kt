package com.nsergio.dev.myrickandmortyapp.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import com.nsergio.dev.myrickandmortyapp.domain.usecase.GetRandomCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class CharacterUIState(
    val characterOfTheDay: SingleCharacterModel? = null
)

class CharactersViewModel(
    private val getRandCharacterUseCase: GetRandomCharacterUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterUIState>(CharacterUIState())
    val state = _state

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(5_000L)
            val character = getRandCharacterUseCase.invoke()
            _state.value = _state.value.copy(characterOfTheDay = character)
        }

    }

}