package com.example.avatar.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Terracotta,
    onPrimary = Cream,
    background = Cream,
    onBackground = Ink,
    surface = FieldBg,
    onSurface = Ink,
    outline = Line
)

private val DarkColors = darkColorScheme(
    primary = Terracotta,
    onPrimary = Cream,
    background = Color(0xFF15120F),
    onBackground = Color(0xFFF1ECE4),
    surface = Color(0xFF1F1B16),
    onSurface = Color(0xFFF1ECE4),
    outline = Color(0xFF3A342E)
)

@Composable
fun AvatarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}