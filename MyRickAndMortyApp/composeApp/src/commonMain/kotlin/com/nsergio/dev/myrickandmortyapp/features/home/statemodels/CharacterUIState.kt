package com.nsergio.dev.myrickandmortyapp.features.home.statemodels

import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CharacterUIState(
    val characterOfTheDay: SingleCharacterModel? = null,
    val charactersPagingData: Flow<PagingData<SingleCharacterModel>> = emptyFlow()
)