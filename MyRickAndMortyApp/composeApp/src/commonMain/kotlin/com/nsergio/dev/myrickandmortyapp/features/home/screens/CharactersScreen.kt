package com.nsergio.dev.myrickandmortyapp.features.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import app.cash.paging.CombinedLoadStates
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.nsergio.dev.myrickandmortyapp.common.RemoteAsyncImage
import com.nsergio.dev.myrickandmortyapp.core.vertical
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import com.nsergio.dev.myrickandmortyapp.features.home.viewmodel.CharactersViewModel
import myrickandmortyapp.composeapp.generated.resources.Res
import myrickandmortyapp.composeapp.generated.resources.rick_face
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    onCharacterClick: (SingleCharacterModel) -> Unit
) {

    val viewModel = koinViewModel<CharactersViewModel>()

    val state by viewModel.state.collectAsState()

    val pagingData = state.charactersPagingData.collectAsLazyPagingItems()

    var hasLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasLoaded) {
            hasLoaded = true
            viewModel.loadCharacterOfTheDay()
            viewModel.loadAllCharacters()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) { paddingValues ->
        Box(
            Modifier
                .statusBarsPadding()
                .padding(paddingValues),
        ) {
            CharactersList(
                characterOFTheDay = state.characterOfTheDay,
                characters = pagingData,
                onCharacterClick = onCharacterClick
            )
        }
    }
}

@Composable
fun CharactersList(
    characterOFTheDay: SingleCharacterModel?,
    characters: LazyPagingItems<SingleCharacterModel>,
    onCharacterClick: (SingleCharacterModel) -> Unit = {}
) {

    val gridItemSpan = GridItemSpan(2)

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        columns = GridCells.Fixed(2),
        state = rememberLazyGridState(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val isEmptyCharacters = characters.itemCount == 0
        val defaultGridSpan = gridItemSpan

        stickyHeader { TitleCharacterOfTheDay() }

        item(span = { gridItemSpan }) {
            UserOfTheDay(characterOFTheDay, onCharacterClick)
        }

        item(span = { gridItemSpan }) {
            TextFillColumns()
        }

        item {
            TextFillOnceColumn()
        }

        stickyHeader {
            TitleListCharacter()
        }

        validateState(
            state = characters.loadState,
            isEmptyCharacters = isEmptyCharacters,
            shouldShowError = {
                item(span = { defaultGridSpan }) {
                    UnknowError()
                }
            },
            shouldShowLoading = {
                item(span = { defaultGridSpan }) {
                    CircularProgress(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            //toma el ancho y la altura se la damos
                            .fillMaxHeight()
                            //la altura
                            .height(100.dp)
                    )
                }
            },

            shouldShowContent = {

                items(characters.itemCount) { index ->
                    val item = characters[index]
                    item?.let {
                        CharacterItemList(item, onCharacterClick)
                    }
                }
            }
        )

    }
}

@Composable
private fun CircularProgress(
    modifier: Modifier,
    colorIndicator: Color = Green,
    contentAlignment: Alignment = Alignment.Center
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
    ) {
        CircularProgressIndicator(color = colorIndicator)
    }
}

@Composable
private fun UnknowError() {
    Text(text = "Unknow error loading characters")
}

@Composable
private fun TitleListCharacter() {
    Surface(
        Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(24))
            .border(
                2.dp, Color.Gray,
                shape = RoundedCornerShape(0, 24, 0, 24)
            )
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            text = "Characters - Sticky Header example"
        )
    }
}

@Composable
private fun TextFillOnceColumn() {
    Text(modifier = Modifier.background(Color.Blue), text = "Item que ocupa 1 col")
}

@Composable
private fun TextFillColumns() {
    Text(modifier = Modifier.background(Color.Red), text = "Texto que ocupa 2 cols")
}

@Composable
private fun TitleCharacterOfTheDay() {
    Text("Character of the day - Sticky Header example")
}

@Composable
fun CharacterItemList(
    characterModel: SingleCharacterModel,
    onCharacterClick: (SingleCharacterModel) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24))
            .border(
                2.dp, Green,
                shape = RoundedCornerShape(0, 24, 0, 24)
            )
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                onCharacterClick.invoke(characterModel)
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = characterModel.image,
            contentDescription = "Character of the day image - ${characterModel.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            placeholder = painterResource(Res.drawable.rick_face)
        )
        Box(
            modifier = Modifier.fillMaxWidth().height(60.dp).background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Black.copy(0f),
                        Color.Black.copy(0.6f),
                        Color.Black.copy(1f),
                    )
                )
            ), contentAlignment = Alignment.Center
        ) {
            Text(characterModel.name, color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun UserOfTheDay(
    model: SingleCharacterModel? = null,
    onCharacterClick: (SingleCharacterModel) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth().size(200.dp, 300.dp), shape = RoundedCornerShape(12)
    ) {
        if (model == null) {
            CircularProgress(modifier = Modifier.fillMaxSize())
        } else {
            Box(contentAlignment = Alignment.BottomStart) {

                RemoteAsyncImage(
                    model = model.image,
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onCharacterClick(model)
                            },
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

private fun validateState(
    state: CombinedLoadStates,
    isEmptyCharacters: Boolean = false,
    shouldShowLoading: () -> Unit,
    shouldShowError: () -> Unit,
    shouldShowContent: () -> Unit
) {
    when {
        state.refresh is LoadState.Error -> {
            shouldShowError.invoke()
        }

        state.refresh is LoadState.Loading && isEmptyCharacters -> {
            shouldShowLoading.invoke()
        }

        else -> {
            shouldShowContent.invoke()
            if (state.append is LoadState.Loading) {
                shouldShowLoading.invoke()
            }
        }
    }
}