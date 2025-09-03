package com.nsergio.dev.myrickandmortyapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nsergio.dev.myrickandmortyapp.data.remote.ApiService
import com.nsergio.dev.myrickandmortyapp.domain.model.SingleCharacterModel

class PagingCharactersSource(
    private val apiService: ApiService
) : PagingSource<Int, SingleCharacterModel>() {

    override fun getRefreshKey(state: PagingState<Int, SingleCharacterModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SingleCharacterModel> {

        try {
            val currentPage = params.key ?: 1
            val response = apiService.getCharacters(params.key ?: 1)
            val characters = response.characters.map { it.toDomain() }

            val nextPage = currentPage + 1
            val prevPage = if (currentPage == 1) null else currentPage - 1

            return LoadResult.Page(
                data = characters,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

}