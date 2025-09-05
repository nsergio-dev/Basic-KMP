package com.nsergio.dev.myrickandmortyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.nsergio.dev.myrickandmortyapp.data.local.entitites.CharacterEntity

@Dao
interface UserPreferenceDAO {

    @Query("SELECT * FROM characters")
    fun getCharacterOfTheDay(): CharacterEntity?
}