package com.nsergio.dev.myrickandmortyapp.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems

enum class TypePaging {
    LAZY_ROW, LAZY_COLUMN, LAZY_VERTICAL_RID
}

@Composable
fun <T : Any> PagingWrapperGeneric(
    pagerItems: LazyPagingItems<T>,
    typePaging: TypePaging,
    initialView: @Composable () -> Unit,
    emptyView: @Composable () -> Unit = { },
    extraItems: @Composable () -> Unit = { },
    itemView: @Composable (T) -> Unit

) {
    val isEmptyItems = pagerItems.itemCount == 0

    when {

        pagerItems.loadState.refresh is LoadState.Loading && isEmptyItems -> {
            //showLoading but a waiting for all items
            initialView.invoke()
        }

        pagerItems.loadState.refresh is LoadState.NotLoading && isEmptyItems -> {
            emptyView()
        }

        else -> {
            //show normal content
            when (typePaging) {
                TypePaging.LAZY_ROW -> BuildLazyRow(pagerItems, itemView)
                TypePaging.LAZY_COLUMN -> BuildLazyColumn(pagerItems, itemView)
                TypePaging.LAZY_VERTICAL_RID -> BuildLazyVerticalGrid(pagerItems, itemView)
            }

            if (pagerItems.loadState.append is LoadState.Loading) {
                //showLoading but loading more items like another page
                extraItems.invoke()
            }
        }

    }


}

@Composable
private fun <T : Any> BuildLazyVerticalGrid(
    pagerItems: LazyPagingItems<T>,
    itemView: @Composable (T) -> Unit
) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        columns = GridCells.Fixed(2),
        state = rememberLazyGridState(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pagerItems.itemCount) { index ->
            val item = pagerItems[index]
            item?.let { safeItem ->
                itemView.invoke(safeItem)
            }
        }
    }
}

@Composable
private fun <T : Any> BuildLazyColumn(
    pagerItems: LazyPagingItems<T>,
    itemView: @Composable (T) -> Unit
) {

    LazyRow {
        items(pagerItems.itemCount) { index ->
            val item = pagerItems[index]
            item?.let { safeItem ->
                itemView.invoke(safeItem)
            }
        }
    }
}

@Composable
private fun <T : Any> BuildLazyRow(
    pagerItems: LazyPagingItems<T>,
    itemView: @Composable (T) -> Unit
) {
    LazyRow {
        items(pagerItems.itemCount) { index ->
            val item = pagerItems[index]
            item?.let { safeItem ->
                itemView.invoke(safeItem)
            }
        }
    }
}