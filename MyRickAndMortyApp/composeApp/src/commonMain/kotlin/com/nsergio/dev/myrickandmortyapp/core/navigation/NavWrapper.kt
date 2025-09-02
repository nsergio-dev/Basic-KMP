package com.nsergio.dev.myrickandmortyapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nsergio.dev.myrickandmortyapp.features.home.ui.HomeScreen


@Composable
fun NavWrapper() {
    val rememberNavController = rememberNavController()

    NavHost(
        navController = rememberNavController,
        startDestination = Destinations.Home.destination
    ) {

        composable(Destinations.Home.destination) {
            HomeScreen()
        }

    }
}