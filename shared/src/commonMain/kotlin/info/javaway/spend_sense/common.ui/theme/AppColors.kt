package info.javaway.spend_sense.common.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val accent: Color,
    val background: Color,
    val surface: Color,
    val onBackground: Color,
    val onSurface: Color
)

val lightPalette = AppColors(
    accent = Color(0xFFFFF59D),
    background = Color(0xFFD7FFEA),
    onBackground = Color(0xFF001329),
    surface = Color(0xFF8DC2A6),
    onSurface = Color(0xFF133050),
)

val darkPalette = AppColors(
    accent = Color(0xFFAF9363),
    background = Color(0xFF060D16),
    onBackground = Color(0xFFF6F6F6),
    surface = Color(0xFF0D1E31),
    onSurface = Color(0xFF99A6B5),
)