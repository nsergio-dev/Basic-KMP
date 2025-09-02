package com.nsergio.dev.myrickandmortyapp.features.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun EpisodesScreen() {

    Scaffold {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.Center,
        ) {
            val hello = helloName()
            Text(text = "Episodes \n\n $hello")
        }
    }
}

//is like a interface than a class must implements to override method
//in this case, ios and android must implement and return his own implementation
expect fun helloName(): String