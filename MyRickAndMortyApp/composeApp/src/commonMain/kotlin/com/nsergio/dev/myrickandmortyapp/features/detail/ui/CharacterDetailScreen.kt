package com.nsergio.dev.myrickandmortyapp.features.detail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nsergio.dev.myrickandmortyapp.core.borderLife
import com.nsergio.dev.myrickandmortyapp.core.ui.Green
import com.nsergio.dev.myrickandmortyapp.core.ui.Pink
import com.nsergio.dev.myrickandmortyapp.core.ui.backgroundPrimary
import com.nsergio.dev.myrickandmortyapp.core.ui.backgroundSecondary
import com.nsergio.dev.myrickandmortyapp.core.ui.backgroundTertiary
import com.nsergio.dev.myrickandmortyapp.core.ui.defaultTextColor
import com.nsergio.dev.myrickandmortyapp.core.ui.tertiaryWhite
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import com.nsergio.dev.myrickandmortyapp.domain.model.LocationCharacterModel
import com.nsergio.dev.myrickandmortyapp.domain.model.OriginCharacterModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SeasonEpisode
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import com.nsergio.dev.myrickandmortyapp.features.detail.statemodels.CharacterDetailUiState
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
                title = { Text("Detail") },
                navigationIcon = {
                    Image(
                        painter = painterResource(Res.drawable.rick_face),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                }
            )
        }
    ) { innerPadding ->
        BodyScreen(innerPadding, uiState, model, episodes, lazyListState)
    }
}

@Composable
private fun BodyScreen(
    innerPadding: PaddingValues,
    uiState: CharacterDetailUiState,
    model: SingleCharacterModel,
    episodes: List<EpisodeModel>?,
    lazyListState: LazyListState
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(backgroundPrimary)
    ) {

        if (uiState.isLoading || uiState.character == null) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Pink,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(60.dp)
                )
            }
        } else {
            MainHeader(model)
            CharacterInformation(model)
            EpisodesList(
                episodes = episodes,
                state = lazyListState,
                onClick = {}
            )
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
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundSecondary)
    ) {

        if (episodes == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Pink)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                state = state,
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = backgroundTertiary)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    model.name,
                    color = defaultTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    model.episode,
                    color = Green,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = model.airDate,
                color = defaultTextColor.copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CharacterInformation(model: SingleCharacterModel) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = backgroundTertiary)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "About the character - ${model.name}",
                color = defaultTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            InformationDetail("Origin: ", model.origin.name)
            Spacer(Modifier.height(4.dp))
            InformationDetail("Location: ", model.location.name)
        }
    }
}

@Composable
fun InformationDetail(title: String, detail: String) {
    Row {
        Text(title, fontWeight = FontWeight.Bold, color = defaultTextColor)
        Text(detail, color = defaultTextColor)
    }
}

@Composable
private fun MainHeader(model: SingleCharacterModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
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
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(backgroundSecondary)
                .padding(top = 90.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = model.name,
                color = defaultTextColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(model.gender, color = defaultTextColor.copy(alpha = 0.8f))
            Text(model.specie, color = defaultTextColor.copy(alpha = 0.8f))
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(12.dp))

            Box(contentAlignment = Alignment.Center) {

                val isAlive = model.status == "alive"

                Box(
                    modifier = Modifier
                        .size(210.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(190.dp)
                            .clip(CircleShape)
                            .borderLife(isAlive),
                        model = model.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(Res.drawable.rick_face)
                    )
                }

                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(top = 160.dp)
                        .clip(RoundedCornerShape(40))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    text = if (isAlive) "Alive" else "Dead",
                    color = if (isAlive) Green else Color.Red,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
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
    val paddingValues = PaddingValues(16.dp)
    val lazyListState = rememberLazyListState()

    val state = CharacterDetailUiState(
        character = model,
        characterEpisodes = listOf(
            EpisodeModel(
                id = 1,
                name = "Rick Sanchez",
                episode = "S01E01",
                url = "",
                season = SeasonEpisode.SEASON_1,
                airDate = "2017-11-10",
                characters = listOf(model.name)
            )
        )
    )

    BodyScreen(
        innerPadding = paddingValues,
        uiState = state,
        lazyListState = lazyListState,
        episodes = state.characterEpisodes,
        model = model
    )

}