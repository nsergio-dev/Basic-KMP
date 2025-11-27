package com.nsergio.dev.myrickandmortyapp.domain

import com.nsergio.dev.myrickandmortyapp.domain.model.SeasonModel

interface SeasonRepository {

    suspend fun getAllSeason(): List<SeasonModel>

}