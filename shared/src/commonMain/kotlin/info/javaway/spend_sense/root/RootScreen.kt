package info.javaway.spend_sense.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.common.ui.AppTheme
import info.javaway.spend_sense.common.ui.AppThemeProvider
import info.javaway.spend_sense.settings.SettingsViewModel
import info.javaway.spend_sense.settings.compose.SettingsScreen

@Composable
fun RootScreen(viewModel: RootViewModel) {

    val state by viewModel.state.collectAsState()

    AppTheme(
        themeIsDark = state.themeIsDark,
        appPrefs = state.appPrefs
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(AppThemeProvider.colors.background)
        ) {

            SettingsScreen(SettingsViewModel())
        }
    }
}