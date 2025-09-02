package com.nsergio.dev.myrickandmortyapp.core.navigation.bottonnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nsergio.dev.myrickandmortyapp.core.navigation.Destinations
import com.nsergio.dev.myrickandmortyapp.features.home.screens.CharactersScreen
import com.nsergio.dev.myrickandmortyapp.features.home.screens.EpisodesScreen

@Composable
fun BottomNavWrapper(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Destinations.Episodes.destination
    ) {

        composable(Destinations.Episodes.destination) {
            EpisodesScreen()
        }

        composable(Destinations.Characters.destination) {
            CharactersScreen()
        }
    }

}


