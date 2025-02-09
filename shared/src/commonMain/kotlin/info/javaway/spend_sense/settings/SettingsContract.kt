package info.javaway.spend_sense.settings

import info.javaway.spend_sense.settings.child.auth.AuthComponent
import info.javaway.spend_sense.settings.child.sync.compose.SyncComponent
import kotlinx.serialization.Serializable

class SettingsContract {

    data class State(
        val info: String,
        val themeIsDark: Boolean,
        val isAuth: Boolean,
        val isLoading: Boolean,
        val email: String
    ) {
        companion object {
            val NONE = State(
                info = "",
                themeIsDark = false,
                isAuth = false,
                isLoading = false,
                email = ""
            )
        }
    }

    sealed interface UiEvent {
        class SwitchTheme(val isDark: Boolean) : UiEvent
    }

    sealed interface Effect {
        data object DataSynced : Effect
        data class Error(val message: String) : Effect
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Auth : Config
        @Serializable
        data object Sync : Config
    }

    sealed interface Child {
        class Auth(val component: AuthComponent) : Child
        class Sync(val component: SyncComponent) : Child
    }
}