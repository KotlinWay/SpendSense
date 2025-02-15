package info.javaway.spend_sense.settings.child.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.base.UiEventHandler
import info.javaway.spend_sense.extensions.componentScope
import info.javaway.spend_sense.settings.child.auth.AuthContract.*
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterComponent
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterContract
import info.javaway.spend_sense.settings.child.auth.child.signin.SignInComponent
import info.javaway.spend_sense.settings.child.auth.child.signin.SignInContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthComponent(
    context: ComponentContext,
    private val registerComponentFactory: RegisterComponent.Factory,
    private val signInComponentFactory: SignInComponent.Factory,
    private val onOutput: (Output) -> Unit
) : ComponentContext by context, UiEventHandler<UiEvent> {

    private val slotNavigation = SlotNavigation<Config>()
    val slots: Value<ChildSlot<*, Child>> = childSlot(
        source = slotNavigation,
        serializer = Config.serializer(),
        childFactory = ::createChild
    )

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ClickOnRegister -> slotNavigation.activate(Config.Register)
            is UiEvent.ClickOnSignIn -> slotNavigation.activate(Config.SignIn)
        }
    }

    private fun createChild(config: Config, context: ComponentContext) = when (config) {
        is Config.Register -> Child.Register(
            registerComponentFactory.create(context, ::onRegisterOutput)
        )

        is Config.SignIn -> Child.SignIn(signInComponentFactory.create(context, ::onSignInOutput))
    }

    private fun onSignInOutput(output: SignInContract.Output) {
        when (output) {
            is SignInContract.Output.Success -> completeLogin()
            is SignInContract.Output.Dismiss -> slotNavigation.dismiss()
        }
    }

    private fun onRegisterOutput(output: RegisterContract.Output) {
        when (output) {
            is RegisterContract.Output.Success -> completeLogin()
            is RegisterContract.Output.Dismiss -> slotNavigation.dismiss()
        }
    }

    private fun completeLogin() {
        componentScope.launch(Dispatchers.Main) {
            slotNavigation.dismiss()
            onOutput(Output.Success)
        }
    }

    class Factory(
        private val registerComponentFactory: RegisterComponent.Factory,
        private val signInComponentFactory: SignInComponent.Factory,
    ) {
        fun create(
            context: ComponentContext,
            onOutput: (Output) -> Unit
        ) = AuthComponent(
            context, registerComponentFactory, signInComponentFactory, onOutput
        )
    }
}