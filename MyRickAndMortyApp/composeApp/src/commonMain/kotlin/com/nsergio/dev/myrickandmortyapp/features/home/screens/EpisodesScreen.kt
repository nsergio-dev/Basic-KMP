package com.nsergio.dev.myrickandmortyapp.features.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsergio.dev.myrickandmortyapp.core.ui.backgroundPrimary
import com.nsergio.dev.myrickandmortyapp.core.ui.backgroundSecondary
import com.nsergio.dev.myrickandmortyapp.core.ui.components.VideoPlayer
import com.nsergio.dev.myrickandmortyapp.core.ui.defaultTextColor
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SeasonEpisode
import com.nsergio.dev.myrickandmortyapp.domain.model.SeasonModel
import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.EpisodesViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesScreen() {

    val viewModel = koinViewModel<EpisodesViewModel>()

    var hasLoaded by rememberSaveable { mutableStateOf(false) }

    val videoUrl by viewModel.videoUrl.collectAsState()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        if (!hasLoaded) {
            hasLoaded = true
            viewModel.loadSeasons()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Episodes",
                        color = defaultTextColor
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (state.seasons != null){
                BodyEpisodes(state.seasons!!, viewModel::openVideo)
            } else {
                CircularProgressIndicator()
            }
        }
        if (videoUrl.isNotBlank()) {
            VideoPlayer(
                modifier = Modifier.size(400.dp),
                videoUrl = videoUrl
            )
        }
    }
}

@Composable
private fun ColumnScope.BodyEpisodes(
    episodes: List<SeasonModel>,
    goToTrailer: (String) -> Unit
) {

    if (episodes.count() <= 0) return

    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 12.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        episodes.forEach { (season, episodes) ->

            stickyHeader {
                TitleSeason(season)
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    items(episodes.size) { index ->
                        EpisodeItemList(model = episodes[index], goToTrailer)
                    }
                }
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun TitleSeason(season: SeasonEpisode) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundPrimary)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = season.name,
            color = defaultTextColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(season.icon),
            contentDescription = null,
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun EpisodeItemList(
    model: EpisodeModel,
    goToTrailer: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { goToTrailer(model.season.trailerUrl) }
            .background(backgroundSecondary)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(model.season.icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = model.episode,
            color = defaultTextColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 1,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

}

//is like a interface than a class must implements to override method
//in this case, ios and android must implement and return his own implementation
expect fun helloName(): String