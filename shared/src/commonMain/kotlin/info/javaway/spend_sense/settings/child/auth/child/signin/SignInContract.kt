package info.javaway.spend_sense.settings.child.auth.child.signin

class SignInContract {

    data class State(
        val email: String,
        val password: String
    )  {
        companion object {
            val NONE = State(
                email = "",
                password = ""
            )
        }
    }

    sealed interface UiEvent {
      class ChangeEmail(val value: String) : UiEvent
      class ChangePassword(val value: String) : UiEvent
      data object Login : UiEvent
      data object Dismiss : UiEvent
    }

    sealed interface Effect {
        data class Error(val message: String) : Effect
    }

    sealed interface Output {
        data object Success : Output
        data object Dismiss : Output
    }
}