package info.javaway.spend_sense.settings.child.auth.child.register

import com.arkivanov.decompose.ComponentContext
import info.javaway.spend_sense.base.StateHolder
import info.javaway.spend_sense.base.UiEventHandler
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.extensions.componentScope
import info.javaway.spend_sense.extensions.updateState
import info.javaway.spend_sense.network.ApiErrorWrapper
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterContract.*
import info.javaway.spend_sense.settings.child.auth.child.register.model.AuthResponse
import info.javaway.spend_sense.settings.child.auth.child.register.model.RegisterRequest
import info.javaway.spend_sense.storage.SettingsManager
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterComponent(
    context: ComponentContext,
    private val api: AppApi,
    private val settings: SettingsManager,
    private val onOutput: (Output) -> Unit
) : ComponentContext by context, StateHolder<State>, UiEventHandler<UiEvent> {

    private val _state = MutableStateFlow(State.NONE)
    override val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    val effects = _effects.asSharedFlow()

    override fun onEvent(event: UiEvent) {
        when(event) {
            is UiEvent.ChangeConfirmPassword -> changeConfirmPassword(event.value)
            is UiEvent.ChangeEmail -> changeEmail(event.value)
            is UiEvent.ChangePassword -> changePassword(event.value)
            is UiEvent.Register -> register()
            is UiEvent.Dismiss -> onOutput(Output.Dismiss)
        }
    }

    private fun changeEmail(email: String) = _state.updateState { copy(email = email) }
    private fun changePassword(pass: String) = _state.updateState { copy(password = pass) }
    private fun changeConfirmPassword(pass: String) = _state.updateState { copy(confirmPassword = pass) }

    private fun register() {
        val registerReq = RegisterRequest(
            email = state.value.email,
            password = state.value.password,
            username = state.value.email
        )
        componentScope.launch(CoroutineExceptionHandler { _, t -> handleError(t) }) {
            val response = api.register(registerReq)
            if (response.status.isSuccess()) {
                val regResponse = response.body<AuthResponse>()
                settings.token = regResponse.jwt.orEmpty()
                settings.email = state.value.email
                withContext(Dispatchers.Main){ onOutput(Output.Success) }
            } else {
                val error = response.body<ApiErrorWrapper>().error
                _effects.emit(Effect.Error(error?.message ?: response.bodyAsText()))
            }
        }
    }

    private fun handleError(error: Throwable) {
        appLog(error.stackTraceToString())
        componentScope.launch {
            _effects.emit(Effect.Error(error.message.orEmpty()))
        }
    }

    class Factory (
        private val api: AppApi,
        private val settings: SettingsManager,
    ){
        fun create(
            context: ComponentContext,
            onOutput: (Output) -> Unit
        ) = RegisterComponent(context, api, settings, onOutput)
    }
}