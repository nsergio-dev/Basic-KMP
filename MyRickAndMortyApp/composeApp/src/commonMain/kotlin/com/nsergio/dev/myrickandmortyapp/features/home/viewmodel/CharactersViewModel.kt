package com.nsergio.dev.myrickandmortyapp.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nsergio.dev.myrickandmortyapp.domain.usecase.character.GetCharactersUseCase
import com.nsergio.dev.myrickandmortyapp.domain.usecase.character.GetRandomCharacterUseCase
import com.nsergio.dev.myrickandmortyapp.features.home.statemodels.CharacterUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getRandCharacterUseCase: GetRandomCharacterUseCase,
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterUIState>(CharacterUIState())
    val state = _state

    /*private val _characters = state.map { state ->
        if (state.characterOfTheDay != null) {
            loadAllCharacters()
        }
    }.stateIn(
        scope = viewModelScope,
        //Sharing is started when the first subscriber appears and never stops
        started = SharingStarted.Lazily,
        initialValue = CharacterUIState()
    )*/

    /**
     * Loads the character of the day from the API and updates the state with the character
     */
    fun loadCharacterOfTheDay() {

        viewModelScope.launch(Dispatchers.IO) {

            val character = getRandCharacterUseCase.invoke()

            //_state.value = _state.value.copy(characterOfTheDay = character)
            _state.update { state ->
                state.copy(characterOfTheDay = character)
            }
        }

    }

    /**
     * Loads all the characters from the API and updates the state with the characters
     */
    fun loadAllCharacters() {

        viewModelScope.launch(Dispatchers.IO) {
            delay(5_000L)
            val characters = getCharactersUseCase.invoke().cachedIn(viewModelScope)
            _state.update { state ->
                state.copy(charactersPagingData = characters)
            }

        }
    }

}

/**
 * Returns a random delay between 1 and 5 seconds
 * @return Long
 */
fun getRandomDelay(): Long {
    return (1_000L..5_000L).random()
}