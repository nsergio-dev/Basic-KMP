package com.nsergio.dev.myrickandmortyapp.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.nsergio.dev.myrickandmortyapp.data.local.dao.UserPreferenceDAO
import com.nsergio.dev.myrickandmortyapp.data.local.entitites.CharacterEntity

const val NAME_DATABASE = "rick_and_morty_db.db"

//not create expect, make project or compile instead
//once compiled kotlin creates a db by self
expect object AppDataBaseConstructor : RoomDatabaseConstructor<RickAndMortyDatabase>

//TODO!! IMPORTANT if you need to test and app crash or something like that,
//try to delete ksp folder and rebuild the project composeApp->build->ksp
@Database(entities = [CharacterEntity::class], version = 1)
@ConstructedBy(AppDataBaseConstructor::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun userPreferenceDao(): UserPreferenceDAO
}