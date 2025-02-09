package info.javaway.spend_sense.root.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import info.javaway.spend_sense.categories.list.compose.CategoriesScreen
import info.javaway.spend_sense.common.ui.theme.AppTheme
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.events.list.compose.EventsScreen
import info.javaway.spend_sense.root.RootComponent
import info.javaway.spend_sense.root.RootContract.*
import info.javaway.spend_sense.settings.compose.SettingsScreen

@Composable
fun RootScreen(component: RootComponent) {

    val state by component.state.collectAsState()
    val stack by component.stack.subscribeAsState()
    val onEvent = component::onEvent

    AppTheme(state.themeIsDark, state.appPrefs) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppThemeProvider.colors.background)
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            RootNavigation(stack)
            RootBottomBar(state.selectedTab) { tab -> onEvent(UiEvent.ClickOnTab(tab)) }
        }
    }
}

@Composable
fun BoxScope.RootNavigation(stack: ChildStack<*, Child>) {
    Children(stack = stack, animation = stackAnimation()) {
        when (val child = it.instance) {
            is Child.Categories -> CategoriesScreen(child.component)
            is Child.Events -> EventsScreen(child.component)
            is Child.Settings -> SettingsScreen(child.component)
        }
    }
}