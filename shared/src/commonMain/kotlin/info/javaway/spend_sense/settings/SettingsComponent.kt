package info.javaway.spend_sense.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.base.StateHolder
import info.javaway.spend_sense.base.UiEventHandler
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.model.CategoryApi
import info.javaway.spend_sense.categories.model.toApi
import info.javaway.spend_sense.categories.model.toEntity
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.model.SpendEventApi
import info.javaway.spend_sense.events.model.toApi
import info.javaway.spend_sense.events.model.toEntity
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.extensions.componentScope
import info.javaway.spend_sense.extensions.updateState
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.settings.SettingsContract.*
import info.javaway.spend_sense.settings.child.auth.AuthComponent
import info.javaway.spend_sense.settings.child.auth.AuthContract
import info.javaway.spend_sense.settings.child.sync.compose.SyncComponent
import info.javaway.spend_sense.settings.child.sync.compose.SyncContract
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class SettingsComponent(
    context: ComponentContext,
    private val deviceInfo: DeviceInfo,
    private val settingsManager: SettingsManager,
    private val authComponentFactory: AuthComponent.Factory,
    private val syncComponentFactory: SyncComponent.Factory
) : ComponentContext by context, StateHolder<State>, UiEventHandler<UiEvent> {

    private val _state = MutableStateFlow(State.NONE)
    override val state = _state

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    private val stackNavigation = StackNavigation<Config>()
    val stack: Value<ChildStack<*, Child>> = childStack(
        source = stackNavigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Auth,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, context: ComponentContext) = when (config) {
        is Config.Auth -> Child.Auth(authComponentFactory.create(context, ::onAuthOutput))
        is Config.Sync -> Child.Sync(syncComponentFactory.create(context, ::onSyncOutput))
    }

    private fun onSyncOutput(output: SyncContract.Output) {
        when (output) {
            is SyncContract.Output.DataSynced -> _effect.tryEmit(Effect.DataSynced)
            is SyncContract.Output.Error -> _effect.tryEmit(Effect.Error(output.string))
        }
    }

    private fun onAuthOutput(output: AuthContract.Output) {
        when (output) {
            is AuthContract.Output.Success -> stackNavigation.replaceAll(Config.Sync)
        }
    }

    init {
        bindToEmail()
        bindToTheme()
        bindToToken()
        initDeviceInfo()
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SwitchTheme -> switchTheme(event.isDark)
        }
    }

    private fun switchTheme(isDark: Boolean) {
        settingsManager.themeIsDark = isDark
    }

    private fun bindToEmail() {
        settingsManager.emailFlow.onEach { email ->
            _state.updateState { copy(email = email) }
        }.launchIn(componentScope)
    }

    private fun bindToToken() {
        settingsManager.tokenFlow.onEach { token ->
            _state.updateState { copy(isAuth = token.isNotBlank()) }
        }.launchIn(componentScope)
    }

    private fun initDeviceInfo() {
        _state.updateState {
            copy(info = deviceInfo.getSummary())
        }
    }

    private fun bindToTheme() {
        settingsManager.themeIsDarkFlow.onEach {
            _state.updateState { copy(themeIsDark = it) }
        }.launchIn(componentScope)
    }

    class Factory(
        private val deviceInfo: DeviceInfo,
        private val settingsManager: SettingsManager,
        private val authComponentFactory: AuthComponent.Factory,
        private val syncComponentFactory: SyncComponent.Factory
    ) {
        fun create(
            context: ComponentContext,
        ) = SettingsComponent(
            context, deviceInfo, settingsManager, authComponentFactory, syncComponentFactory
        )
    }
}