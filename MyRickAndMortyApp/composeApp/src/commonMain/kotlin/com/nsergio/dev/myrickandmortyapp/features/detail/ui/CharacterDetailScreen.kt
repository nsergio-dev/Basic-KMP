package com.nsergio.dev.myrickandmortyapp.features.detail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nsergio.dev.myrickandmortyapp.core.borderLife
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import com.nsergio.dev.myrickandmortyapp.domain.model.LocationCharacterModel
import com.nsergio.dev.myrickandmortyapp.domain.model.OriginCharacterModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.CharacterDetailViewModel
import myrickandmortyapp.composeapp.generated.resources.Res
import myrickandmortyapp.composeapp.generated.resources.background_space
import myrickandmortyapp.composeapp.generated.resources.rick_face
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    model: SingleCharacterModel,
    onBack: () -> Unit = {}
) {

    val lazyListState = rememberLazyListState()
    val viewModel = koinViewModel<CharacterDetailViewModel>(
        parameters = { parametersOf(model) }
    )

    val uiState by viewModel.uiState.collectAsState()

    val episodes = uiState.characterEpisodes
    LaunchedEffect(Unit) {
        viewModel.getEpisodes(model.episodes)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Detail")
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
        ) {

            if (uiState.isLoading || uiState.character == null) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(20.dp).fillMaxSize()
                )
            } else {
                MainHeader(model = model)
                CharacterInformation(model = model)
                EpisodesList(
                    episodes = episodes,
                    state = lazyListState,
                    onClick =  {

                    }
                )
            }
        }
    }

}

@Composable
private fun ColumnScope.EpisodesList(
    episodes: List<EpisodeModel>?,
    state: LazyListState,
    onClick: (EpisodeModel) -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {

        if (episodes == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(20.dp).fillMaxSize(),
                    color = Color.Green
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = state,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(episodes) { item ->
                    EpisodeItem(item)
                }

            }
        }
    }
}

@Composable
fun EpisodeItem(model: EpisodeModel) {
    Text(model.name)
    Text(model.episode)
}

@Composable
fun CharacterInformation(model: SingleCharacterModel) {
    ElevatedCard(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text("About the character - ${model.name}")
            InformationDetail("Origin: ", model.origin.name)
            InformationDetail("Location: ", model.location.name)
        }
    }
}

@Composable
fun InformationDetail(title: String, detail: String) {
    Row {
        Text(title, fontWeight = FontWeight.Bold, color = Color.White)
        Text(detail, color = Color.Green)
    }
}

@Composable
private fun MainHeader(model: SingleCharacterModel) {

    Box(
        modifier = Modifier.fillMaxWidth().height(300.dp)
    ) {

        Image(
            painter = painterResource(Res.drawable.background_space),
            contentDescription = "Background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        CharacterHeader(model)

    }
}

@Composable
private fun CharacterHeader(
    model: SingleCharacterModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        topStartPercent = 10,
                        topEndPercent = 10
                    )
                )
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))
            Text(
                text = model.name,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = model.gender, color = Color.Black)
            Text(text = model.specie, color = Color.Black)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(15.dp))
            Box(contentAlignment = Alignment.TopCenter) {
                val isAlive = model.status == "alive"
                Box(
                    modifier = Modifier
                        .size(205.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(190.dp)
                            .clip(CircleShape)
                            .borderLife(isAlive),
                        model = model.image,
                        contentDescription = "Character of the day image - ${model.name}",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(Res.drawable.rick_face)
                    )
                }
                Text(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(percent = 30)
                        )
                        .background(Color.White)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    text = if (isAlive) "Alive" else "Dead",
                    color = if (isAlive) Color.Green else Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview()
@Composable
fun CharacterDetailScreenPreview() {
    val model = SingleCharacterModel(
        id = 1,
        name = "Rick Sanchez",
        status = "alive",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        specie = "Human",
        origin = OriginCharacterModel(),
        location = LocationCharacterModel(),
        episodes = listOf(),
        url = "",
        created = "",
    )
    MainHeader(model = model)
}