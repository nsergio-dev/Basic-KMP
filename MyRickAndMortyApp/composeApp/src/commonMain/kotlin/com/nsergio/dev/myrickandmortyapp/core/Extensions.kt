package com.nsergio.dev.myrickandmortyapp.core

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp


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

fun Modifier.borderLife(isAlive: Boolean): Modifier {
    val color = if (isAlive) Color.Green else Color.Red
    return border(4.dp, color, CircleShape)
}