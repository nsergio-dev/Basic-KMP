package com.nsergio.dev.myrickandmortyapp.features.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nsergio.dev.myrickandmortyapp.common.RemoteAsyncImage
import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.CharactersViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen() {

    val viewModel = koinViewModel<CharactersViewModel>()

    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold { paddingValues ->
        Column(
            Modifier
                .statusBarsPadding()
                .background(Color.Cyan)
                .padding(paddingValues),
        ) {
            if (state.characterOfTheDay == null) {
                Box {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(300.dp, 300.dp)
                            .align(Alignment.Center)
                    )
                }
            } else {

                LoadCharacterImage(state.characterOfTheDay!!.image)
                Text(
                    textDecoration = TextDecoration.LineThrough,
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ut dignissim est. Proin ut nisi ut lacus volutpat mollis quis a odio. Donec et tellus feugiat, efficitur urna ultrices, mattis orci. Nunc augue felis, viverra eget sagittis vel, tristique at lacus.Name of character lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                )
            }
        }
    }
}

@Composable
fun LoadCharacterImage(
    imageUrl: String
) {
    RemoteAsyncImage(
        model = imageUrl,
        crossfade = true,
        onContentReady = { painter ->
            Image(
                painter = painter,
                contentDescription = "Character of the day image",
            )
        },
        onLoading = {
            Box {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(300.dp, 300.dp)
                        .align(Alignment.Center)
                )
            }
        }
    )

}