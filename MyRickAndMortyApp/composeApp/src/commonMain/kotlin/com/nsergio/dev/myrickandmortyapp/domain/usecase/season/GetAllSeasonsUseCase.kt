package com.nsergio.dev.myrickandmortyapp.domain.usecase.season

import com.nsergio.dev.myrickandmortyapp.domain.SeasonRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.SeasonModel

class GetAllSeasonsUseCase(
    private val repository: SeasonRepository
) {

    suspend operator fun invoke(): List<SeasonModel> {
        val pager = repository.getAllSeason()
        return pager
    }

}