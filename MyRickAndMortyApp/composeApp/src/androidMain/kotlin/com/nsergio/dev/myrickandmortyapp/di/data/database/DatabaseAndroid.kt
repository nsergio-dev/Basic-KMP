package com.nsergio.dev.myrickandmortyapp.di.data.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.nsergio.dev.myrickandmortyapp.data.local.NAME_DATABASE
import com.nsergio.dev.myrickandmortyapp.data.local.RickAndMortyDatabase
import kotlinx.coroutines.Dispatchers

fun getDatabase(context: Context): RickAndMortyDatabase {

    val dbFile = context.getDatabasePath(NAME_DATABASE)

    return Room
        .databaseBuilder<RickAndMortyDatabase>(context = context, name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}