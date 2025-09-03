package com.nsergio.dev.myrickandmortyapp.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nsergio.dev.myrickandmortyapp.core.navigation.bottonnavigation.BottomBarItem
import com.nsergio.dev.myrickandmortyapp.core.navigation.bottonnavigation.BottomNavWrapper


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val bottomBarItems = BottomBarItem.entries.toList()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Rick and Morty App") })
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                items = bottomBarItems
            )
        },
        content = {
            Box(Modifier.fillMaxSize()) {
                //Text("Home Screen")
                BottomNavWrapper(navController)
            }
        }
    )
}

@Composable
fun BottomBar(
    navController: NavController,
    items: List<BottomBarItem>
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    BottomAppBar {
        items.forEach { bottomBarItem ->
            val isSelected = currentDestination
                ?.hierarchy
                ?.any { it.route == bottomBarItem.navigateTo } == true

            NavigationBarItem(
                selected = isSelected,
                label = { Text(text = bottomBarItem.title) },
                icon = bottomBarItem.icon,
                onClick = {
                    //navigate to
                    navController.navigate(
                        route = bottomBarItem.navigateTo
                    ) {
                        //find out the start destination and recycle from the backstack
                        //to avoid multiple copies of the same destination
                        navController.graph.startDestinationRoute?.let { safeRoute ->
                            popUpTo(safeRoute) {
                                saveState = true
                            }
                            //avoid multiple copies of the same destination
                            launchSingleTop = true
                            //restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

/*
@Preview
@Composable
fun PreviewHome() {
    Box(Modifier.fillMaxSize()) {
        Text("Home Screen")
    }
}*/
