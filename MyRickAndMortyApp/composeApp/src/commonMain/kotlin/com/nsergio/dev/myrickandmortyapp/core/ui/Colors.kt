package com.nsergio.dev.myrickandmortyapp.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Pink = Color(0xFFFF4F84)
val Green = Color(0xFF54D68B)

val primaryWhite = Color(0xFFFFFFFF)
val secondaryWhite = Color(0xFFF2F2F7)
val tertiaryWhite = Color(0xFFF7F7FA)

val primaryBlack = Color(0xFF000000)
val secondaryBlack = Color(0xFF1E1E1E)
val tertiaryBlack = Color(0xFF2C2C2C)

val backgroundPrimary
    @Composable get() = if (isSystemInDarkTheme()) primaryBlack else secondaryWhite

val backgroundSecondary
    @Composable get() = if (isSystemInDarkTheme()) secondaryBlack else primaryWhite

val backgroundTertiary
    @Composable get() = if (isSystemInDarkTheme()) tertiaryBlack else tertiaryWhite

val defaultTextColor
    @Composable get() = if (isSystemInDarkTheme()) Color.White else Color(0xFF1D1D1D)

