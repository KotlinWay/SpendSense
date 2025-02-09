package info.javaway.spend_sense.settings.child.sync.compose

interface SyncContract {
    data class State(
        val isLoading: Boolean = false,
        val email: String = ""
    )

    sealed interface UiEvent {
        data object Sync : UiEvent
        data object Logout : UiEvent
    }

    sealed interface Output {
        class Error(val string: String) : Output
        data object DataSynced : Output
    }
}