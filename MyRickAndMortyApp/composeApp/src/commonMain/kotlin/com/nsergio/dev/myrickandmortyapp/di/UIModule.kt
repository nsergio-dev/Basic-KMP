package com.nsergio.dev.myrickandmortyapp.di

import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.CharactersViewModel
import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.EpisodesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::EpisodesViewModel)
    viewModelOf(::CharactersViewModel)
}