package info.javaway.spend_sense.root

import androidx.compose.runtime.Composable
import info.javaway.spend_sense.settings.compose.SettingsScreen
import info.javaway.spend_sense.settings.SettingsViewModel

@Composable
fun RootScreen() {
    SettingsScreen(SettingsViewModel())
}