package com.nsergio.dev.myrickandmortyapp.core.navigation.bottonnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nsergio.dev.myrickandmortyapp.core.navigation.Destinations
import com.nsergio.dev.myrickandmortyapp.features.home.screens.CharactersScreen
import com.nsergio.dev.myrickandmortyapp.features.home.screens.EpisodesScreen
import kotlinx.serialization.json.Json

@Composable
fun BottomNavWrapper(navController: NavHostController, mainNavHostController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Destinations.Episodes.destination
    ) {

        composable(Destinations.Episodes.destination) {
            EpisodesScreen()
        }

        composable(route = Destinations.Characters.destination) {
            CharactersScreen(
                onCharacterClick = {
                    val encode = Json.encodeToString(it)
                    mainNavHostController.navigate(
                        Destinations.CharacterDetail(model = encode)
                    )
                }
            )
        }
    }

}


