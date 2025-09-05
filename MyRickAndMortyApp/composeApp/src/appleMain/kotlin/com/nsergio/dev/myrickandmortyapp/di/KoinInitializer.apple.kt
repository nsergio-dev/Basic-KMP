package com.nsergio.dev.myrickandmortyapp.di

import com.nsergio.dev.myrickandmortyapp.data.database.getDatabaseIOS
import com.nsergio.dev.myrickandmortyapp.data.local.RickAndMortyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * provides a module for koin with the platform Apple like iOS
 */
actual fun platformModule(): Module {
    //module or modules for ios
    return module {
        single <RickAndMortyDatabase> { getDatabaseIOS() }
    }
}