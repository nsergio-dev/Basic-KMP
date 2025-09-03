package com.nsergio.dev.myrickandmortyapp.features.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsergio.dev.myrickandmortyapp.common.RemoteAsyncImage
import com.nsergio.dev.myrickandmortyapp.core.vertical
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.CharactersViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen() {

    val viewModel = koinViewModel<CharactersViewModel>()
    val state by viewModel.state.collectAsState()
    var hasLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasLoaded) {
            hasLoaded = true
            viewModel.loadCharacterOfTheDay()
            viewModel.loadAllCharacters()
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) { paddingValues ->
        Column(
            Modifier
                .statusBarsPadding()
                .padding(paddingValues),
        ) {
            UserOfTheDay(state.characterOfTheDay)
        }
    }
}

@Composable
fun UserOfTheDay(
    model: SingleCharacterModel? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 30.dp)
            .height(400.dp), shape = RoundedCornerShape(12)
    ) {
        if (model == null) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = Green)
            }
        } else {
            Box(contentAlignment = Alignment.BottomStart) {
                /*Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Blue.copy(alpha = 0.5f))
                )*/

                RemoteAsyncImage(
                    model = model.image,
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = it,
                        contentDescription = "Character of the day image",
                        contentScale = ContentScale.Crop,
                    )
                }
                Gradient()
                Text(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .fillMaxHeight()
                        .vertical()
                        .rotate(-90f),
                    text = model.name,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 44.sp,
                    maxLines = 1,
                    minLines = 1,
                    overflow = TextOverflow.Ellipsis


                )
            }
        }
    }
}

@Composable
fun Gradient() {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    0f to Color.Black.copy(alpha = 0.5f),
                    0.5f to Color.Blue.copy(alpha = 0.2f),
                    1f to Color.White.copy(alpha = 0f)
                )
            )
    )
}