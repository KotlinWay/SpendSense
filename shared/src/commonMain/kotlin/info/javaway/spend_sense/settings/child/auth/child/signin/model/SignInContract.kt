package info.javaway.spend_sense.settings.child.auth.child.signin.model

import info.javaway.spend_sense.base.BaseEvent
import info.javaway.spend_sense.base.BaseViewState

class SignInContract {

    data class State(
        val email: String,
        val password: String
    ) : BaseViewState {
        companion object {
            val NONE = State(
                email = "",
                password = ""
            )
        }
    }

    sealed interface Event : BaseEvent {
        data object Success : Event
        data class Error(val message: String) : Event
    }
}