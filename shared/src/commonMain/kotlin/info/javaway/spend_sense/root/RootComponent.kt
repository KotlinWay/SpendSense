package info.javaway.spend_sense.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.base.StateHolder
import info.javaway.spend_sense.base.UiEventHandler
import info.javaway.spend_sense.categories.list.CategoriesComponent
import info.javaway.spend_sense.events.list.EventsComponent
import info.javaway.spend_sense.extensions.componentScope
import info.javaway.spend_sense.extensions.updateState
import info.javaway.spend_sense.root.RootContract.*
import info.javaway.spend_sense.root.model.AppTab
import info.javaway.spend_sense.settings.SettingsComponent
import info.javaway.spend_sense.settings.SettingsContract
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent

class RootComponent(
    context: ComponentContext,
    settingsManager: SettingsManager,
    private val eventsFactory: EventsComponent.Factory,
    private val categoriesFactory: CategoriesComponent.Factory,
    private val settingsFactory: SettingsComponent.Factory
) : ComponentContext by context, StateHolder<State>, UiEventHandler<UiEvent> {

    private val _state =
        MutableStateFlow(State.NONE.copy(themeIsDark = settingsManager.themeIsDark))
    override val state = _state.asStateFlow()

    private val stackNavigation = StackNavigation<Config>()
    val stack: Value<ChildStack<*, Child>> = childStack(
        source = stackNavigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Events,
        childFactory = ::createChildStack
    )

    init {
        settingsManager.themeIsDarkFlow.onEach {
            _state.updateState { copy(themeIsDark = it) }
        }.launchIn(componentScope)
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ClickOnTab -> clickOnTab(event.tab)
        }
    }

    private fun clickOnTab(tab: AppTab) {
        _state.updateState { copy(selectedTab = tab) }
        val config = tab.toConfig()
        stackNavigation.bringToFront(config)
    }

    private fun createChildStack(config: Config, context: ComponentContext) = when (config) {
        Config.Categories -> Child.Categories(categoriesFactory.create(context))
        Config.Events -> Child.Events(eventsFactory.create(context))
        Config.Settings -> Child.Settings(settingsFactory.create(context))
    }

    class Factory(
        private val settingsManager: SettingsManager,
        private val eventsFactory: EventsComponent.Factory,
        private val categoriesFactory: CategoriesComponent.Factory,
        private val settingsFactory: SettingsComponent.Factory
    ) {
        fun create(context: ComponentContext) = RootComponent(
            context, settingsManager, eventsFactory, categoriesFactory, settingsFactory
        )
    }
}

private fun AppTab.toConfig(): Config = when(this){
    AppTab.Categories -> Config.Categories
    AppTab.Events -> Config.Events
    AppTab.Settings -> Config.Settings
}