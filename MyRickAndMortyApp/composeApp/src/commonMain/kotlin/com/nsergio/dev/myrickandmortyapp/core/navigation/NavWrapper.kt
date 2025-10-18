package com.nsergio.dev.myrickandmortyapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import com.nsergio.dev.myrickandmortyapp.features.detail.ui.CharacterDetailScreen
import com.nsergio.dev.myrickandmortyapp.features.home.ui.HomeScreen
import kotlinx.serialization.json.Json


@Composable
fun NavWrapper() {
    val mainNavController = rememberNavController()

    NavHost(
        navController = mainNavController,
        startDestination = Destinations.Home.destination
    ) {

        composable(Destinations.Home.destination) {
            HomeScreen(mainNavController)
        }

        composable<Destinations.CharacterDetail> { navBackStackEntry ->
            val modelString = navBackStackEntry.toRoute<Destinations.CharacterDetail>()
            val model = Json.decodeFromString<SingleCharacterModel>(modelString.model)
            CharacterDetailScreen(
                model = model,
                onBack = {
                    mainNavController.popBackStack()
                }
            )
        }

    }
}