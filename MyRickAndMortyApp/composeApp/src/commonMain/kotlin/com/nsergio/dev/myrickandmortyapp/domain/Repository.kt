package com.nsergio.dev.myrickandmortyapp.domain

import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel

interface Repository {
    suspend fun getSingleCharacter(id: Int): SingleCharacterModel
}