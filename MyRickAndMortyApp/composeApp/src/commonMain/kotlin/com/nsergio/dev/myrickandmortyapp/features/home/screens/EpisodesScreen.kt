package com.nsergio.dev.myrickandmortyapp.features.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.nsergio.dev.myrickandmortyapp.core.ui.components.PagingProgressBarIndicator
import com.nsergio.dev.myrickandmortyapp.core.ui.components.PagingWrapperGeneric
import com.nsergio.dev.myrickandmortyapp.core.ui.components.TypePaging
import com.nsergio.dev.myrickandmortyapp.core.ui.components.VideoPlayer
import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
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

    val episodesPager: LazyPagingItems<EpisodeModel> =
        state.charactersPagingData.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        if (!hasLoaded) {
            hasLoaded = true
            viewModel.loadEpisodes()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Episodes")
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            PagingWrapperGeneric(
                pagerItems = episodesPager,
                typePaging = TypePaging.LAZY_ROW,
                initialView = { PagingProgressBarIndicator(Modifier.fillMaxSize()) },
                itemView = {
                    EpisodeItemList(model = it, goToTrailer = viewModel::openVideo)
                }
            )
            if (videoUrl.isNotBlank()) {
                VideoPlayer(
                    modifier = Modifier.size(400.dp),
                    videoUrl = videoUrl
                )
            }
        }
    }
}

@Composable
fun EpisodeItemList(
    model: EpisodeModel,
    goToTrailer: (String) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .width(120.dp)
            .padding(horizontal = 8.dp)
            .clickable {
                goToTrailer.invoke(model.season.trailerUrl)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12))
        ) {

            Image(
                modifier = Modifier
                    .fillMaxSize(),
                contentDescription = "Season poster",
                painter = painterResource(model.season.icon),
                contentScale = ContentScale.Crop
            )
        }

        Text(model.episode, fontWeight = FontWeight.Bold)
    }

}

//is like a interface than a class must implements to override method
//in this case, ios and android must implement and return his own implementation
expect fun helloName(): String