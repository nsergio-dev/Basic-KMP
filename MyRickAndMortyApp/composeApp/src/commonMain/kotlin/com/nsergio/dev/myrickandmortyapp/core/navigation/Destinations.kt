package com.nsergio.dev.myrickandmortyapp.core.navigation

sealed class Destinations(val destination: String) {

    data object Home : Destinations("home")

    //bottom barDestinies

    data object Episodes : Destinations("episodes")
    data object Characters : Destinations("characters")

}