package com.nsergio.dev.myrickandmortyapp.di

import com.nsergio.dev.myrickandmortyapp.data.local.RickAndMortyDatabase
import com.nsergio.dev.myrickandmortyapp.di.data.database.getDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * provides a module for koin with the platform Android
 */
actual fun platformModule(): Module {

    return module {
        single <RickAndMortyDatabase> { getDatabase(get()) }
    }
}