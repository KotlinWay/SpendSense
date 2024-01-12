package info.javaway.spend_sense.settings.child.auth.child.register

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.network.ApiErrorWrapper
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.settings.child.auth.child.register.model.AuthResponse
import info.javaway.spend_sense.settings.child.auth.child.register.model.RegisterContract.Event
import info.javaway.spend_sense.settings.child.auth.child.register.model.RegisterContract.State
import info.javaway.spend_sense.settings.child.auth.child.register.model.RegisterRequest
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val api: AppApi,
    private val settings: SettingsManager
) : BaseViewModel<State, Event>() {


    fun changeEmail(email: String) = updateState { copy(email = email) }
    fun changePassword(pass: String) = updateState { copy(password = pass) }
    fun changeConfirmPassword(pass: String) = updateState { copy(confirmPassword = pass) }

    fun register() {
        val registerReq = RegisterRequest(
            email = state.value.email,
            password = state.value.password,
            username = state.value.email
        )
        viewModelScope.launch(
            CoroutineExceptionHandler { _, t ->
                appLog(t.stackTraceToString())
                pushEvent(Event.Error(t.message.orEmpty()))
            }
        ) {
            val response = api.register(registerReq)
            if (response.status.isSuccess()) {
                val regResponse = response.body<AuthResponse>()
                settings.token = regResponse.jwt.orEmpty()
                settings.email = state.value.email
                pushEvent(Event.Success)
            } else {
                val error = response.body<ApiErrorWrapper>().error
                pushEvent(Event.Error(error?.message ?: response.bodyAsText()))
            }
        }
    }


    override fun initialState() = State.NONE
}