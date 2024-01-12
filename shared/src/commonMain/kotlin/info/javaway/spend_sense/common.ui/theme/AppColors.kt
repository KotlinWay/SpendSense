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
    accent = Color(0xFFa3592f),
    background = Color(0xFFfcf5e7),
    onBackground = Color(0xFF351904),
    surface = Color(0xFFfade9d),
    onSurface = Color(0xFF5d4b37),
)

val darkPalette = AppColors(
    accent = Color(0xFFAF9363),
    background = Color(0xFF060D16),
    onBackground = Color(0xFFF6F6F6),
    surface = Color(0xFF0D1E31),
    onSurface = Color(0xFF99A6B5),
)