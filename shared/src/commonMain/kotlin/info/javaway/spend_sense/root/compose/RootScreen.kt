package info.javaway.spend_sense.root.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.categories.CategoriesScreen
import info.javaway.spend_sense.common.ui.theme.AppTheme
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.events.EventsScreen
import info.javaway.spend_sense.root.RootViewModel
import info.javaway.spend_sense.root.model.AppTab
import info.javaway.spend_sense.settings.compose.SettingsScreen

@Composable
fun RootScreen() {

    val viewModel = getKoinInstance<RootViewModel>()
    val state by viewModel.state.collectAsState()

    AppTheme(state.themeIsDark, state.appPrefs) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppThemeProvider.colors.background)
        ) {

            RootNavigation(state.selectedTab)
            RootBottomBar(state.selectedTab) { tab ->
                viewModel.handleClickOnTab(tab)
            }
        }
    }
}

@Composable
fun BoxScope.RootNavigation(selectedTab: AppTab){

    when(selectedTab){
        AppTab.Categories -> CategoriesScreen()
        AppTab.Events -> EventsScreen()
        AppTab.Settings -> SettingsScreen(getKoinInstance())
    }

}
