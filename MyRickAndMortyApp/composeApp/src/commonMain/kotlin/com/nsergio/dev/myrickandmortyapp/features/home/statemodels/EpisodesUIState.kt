package com.nsergio.dev.myrickandmortyapp.features.home.statemodels

import com.nsergio.dev.myrickandmortyapp.domain.model.SeasonModel

data class EpisodesUIState(

    val seasons: List<SeasonModel>? = null

)