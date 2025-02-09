package info.javaway.spend_sense.root

import info.javaway.spend_sense.categories.list.CategoriesComponent
import info.javaway.spend_sense.common.ui.theme.AppPrefs
import info.javaway.spend_sense.events.list.EventsComponent
import info.javaway.spend_sense.root.model.AppTab
import info.javaway.spend_sense.settings.SettingsComponent
import kotlinx.serialization.Serializable

class RootContract {

    data class State(
        val themeIsDark: Boolean,
        val firstDayIsMonday: Boolean,
        val selectedTab: AppTab
    ){

        val appPrefs: AppPrefs
            get() = AppPrefs(firstDayIsMonday = firstDayIsMonday)

        companion object {
            val NONE = State(
                themeIsDark = false,
                firstDayIsMonday = true,
                selectedTab = AppTab.Events
            )
        }
    }

    sealed interface UiEvent {
        class ClickOnTab(val tab: AppTab) : UiEvent
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Events : Config
        @Serializable
        data object Categories : Config
        @Serializable
        data object Settings : Config
    }

    sealed interface Child {
        class Events(val component: EventsComponent) : Child
        class Categories(val component: CategoriesComponent) : Child
        class Settings(val component: SettingsComponent) : Child
    }
}