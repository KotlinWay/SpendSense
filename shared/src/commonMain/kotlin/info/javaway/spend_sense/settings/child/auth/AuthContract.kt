package info.javaway.spend_sense.settings.child.auth

import info.javaway.spend_sense.settings.child.auth.child.register.RegisterComponent
import info.javaway.spend_sense.settings.child.auth.child.signin.SignInComponent
import kotlinx.serialization.Serializable

class AuthContract {

    sealed interface UiEvent {
        data object ClickOnSignIn : UiEvent
        data object ClickOnRegister : UiEvent
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Register : Config
        @Serializable
        data object SignIn: Config
    }

    sealed interface Child {
        class Register(val component: RegisterComponent) : Child
        class SignIn(val component: SignInComponent): Child
    }

    sealed interface Output {
        data object Success : Output
    }
}