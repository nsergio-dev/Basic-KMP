package com.nsergio.dev.myrickandmortyapp.domain

interface Repository {
    suspend fun getSingleCharacter(id: Int)
}