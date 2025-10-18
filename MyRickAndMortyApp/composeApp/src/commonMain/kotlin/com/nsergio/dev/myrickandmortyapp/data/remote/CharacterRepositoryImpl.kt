package com.nsergio.dev.myrickandmortyapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nsergio.dev.myrickandmortyapp.data.local.RickAndMortyDatabase
import com.nsergio.dev.myrickandmortyapp.data.remote.paging.PagingCharactersSource
import com.nsergio.dev.myrickandmortyapp.data.remote.responses.episodes.EpisodeItemResponse
import com.nsergio.dev.myrickandmortyapp.domain.CharacterRepository
import com.nsergio.dev.myrickandmortyapp.domain.model.CharacterOfTheDayModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(
    private val apiService: ApiService,
    private val pagingSource: PagingCharactersSource,
    private val database: RickAndMortyDatabase
) : CharacterRepository {

    override suspend fun getSingleCharacter(id: Int): SingleCharacterModel {
        val apiResponse = apiService.getSingleCharacter(id)
        val response = apiResponse.toDomain()
        return response

    }

    override suspend fun getCharacters(): Flow<PagingData<SingleCharacterModel>> {

        val pagerConfig = PagingConfig(
            pageSize = ITEMS_PER_PAGE,
            prefetchDistance = ITEMS_TO_LOAD_NEW_ITEMS
        )

        val pager = Pager(
            config = pagerConfig,
            pagingSourceFactory = { pagingSource }
        ).flow

        return pager
    }

    override suspend fun getCharacterFromDB(): CharacterOfTheDayModel? {
        val characterModel = database.userPreferenceDao().getCharacterOfTheDay()
        val model = characterModel?.toDomain()
        return model
    }

    override suspend fun saveCharacterOfTheDay(
        characterModel: SingleCharacterModel,
        currentDay: String
    ) {
        val entity = characterModel.toEntity(currentDay)
        database.userPreferenceDao().createCharacterOfTheDay(characterEntity = entity)
    }

    override suspend fun getEpisodesFromAPI(episodesId: List<String>): List<EpisodeItemResponse> {
        val episodes = apiService.getEpisodes(episodesId)
        return episodes
    }

    companion object Companion {
        const val ITEMS_PER_PAGE = 20
        const val ITEMS_TO_LOAD_NEW_ITEMS = 5
    }

}

