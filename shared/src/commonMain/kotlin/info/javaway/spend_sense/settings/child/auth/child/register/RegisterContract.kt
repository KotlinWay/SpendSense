package info.javaway.spend_sense.settings.child.auth.child.register


class RegisterContract {

    data class State(
        val email: String,
        val password: String,
        val confirmPassword: String
    )  {

        val sendIsActive: Boolean
            get() = password.isNotBlank() && confirmPassword.isNotBlank() && password == confirmPassword

        companion object {
            val NONE = State(
                email = "",
                password = "",
                confirmPassword = ""
            )
        }
    }

    sealed interface UiEvent {
        class ChangeEmail(val value: String) : UiEvent
        class ChangePassword(val value: String) : UiEvent
        class ChangeConfirmPassword(val value: String) : UiEvent
        data object Register : UiEvent
        data object Dismiss : UiEvent
    }

    sealed interface Output {
        data object Dismiss : Output
        data object Success : Output
    }

    sealed interface Effect {
        class Error(val message: String) : Effect
    }
}