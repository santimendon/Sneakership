package com.smendon.sneakersapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColors(
    primary = GreyTint,
    secondary = Salmon,
    background = Color.White
)

private val LightColorScheme = lightColors(
    background = GreyTint,
    onBackground = Color.White,
    surface = Color.White,
    primary = GreyTint,
    secondary = Salmon,
    onPrimary = Color.White,
    onSecondary = Color.White
)

@Composable
fun SneakersAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val typography = if (darkTheme) {
        DarkTypography
    } else {
        LightTypography
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        content = content
    )
}