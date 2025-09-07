package com.nsergio.dev.myrickandmortyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nsergio.dev.myrickandmortyapp.data.local.entitites.CharacterEntity

@Dao
interface UserPreferenceDAO {

    @Query("SELECT * FROM characters")
    suspend fun getCharacterOfTheDay(): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = CharacterEntity::class)
    suspend fun createCharacterOfTheDay(characterEntity: CharacterEntity)
}