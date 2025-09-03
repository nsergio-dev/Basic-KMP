package com.nsergio.dev.myrickandmortyapp.core

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout


fun Modifier.vertical() = layout { mesurable, constraint ->
    val placeable = mesurable.measure(constraint)
    layout(placeable.height, placeable.width) {
        val placeableWidth = placeable.width / 2
        val placeableHeight = placeable.height / 2
        placeable.place(
            x = -(placeableWidth - placeableHeight),
            y = -(placeableHeight - placeableWidth)
        )

    }
}