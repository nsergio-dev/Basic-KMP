package com.nsergio.dev.myrickandmortyapp.core.navigation.bottonnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.nsergio.dev.myrickandmortyapp.core.navigation.Destinations

enum class BottomBarItem {

    EPISODES {
        override val title: String = "Episodes"
        override val navigateTo: String = Destinations.Episodes.destination
        override val icon: @Composable () -> Unit = {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "button episodes"
            )
        }
    },

    CHARACTERS {

        override val title: String = "Characters"
        override val navigateTo: String = Destinations.Characters.destination
        override val icon: @Composable () -> Unit = {
            Icon(
                imageVector = Icons.Default.People,
                contentDescription = "button characters"
            )
        }
    };

    abstract val title: String
    abstract val navigateTo: String
    abstract val icon: @Composable () -> Unit
}