package info.javaway.spend_sense.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

data class AppPrefs(
    val firstDayIsMonday: Boolean = true
)

val LocalAppPrefs = staticCompositionLocalOf { AppPrefs() }
val LocalAppColors = staticCompositionLocalOf { darkPalette }

@Composable
fun AppTheme(
    themeIsDark: Boolean,
    appPrefs: AppPrefs,
    content: @Composable () -> Unit
) {
    val colors = if (themeIsDark) darkPalette else lightPalette

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppPrefs provides appPrefs,
        content = content
    )
}

object AppThemeProvider {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val appPrefs: AppPrefs
        @Composable
        @ReadOnlyComposable
        get() = LocalAppPrefs.current
}







