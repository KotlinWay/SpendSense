package info.javaway.spend_sense.settings.child.sync.compose

import com.arkivanov.decompose.ComponentContext
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
import info.javaway.spend_sense.settings.child.sync.compose.SyncContract.Output
import info.javaway.spend_sense.settings.child.sync.compose.SyncContract.State
import info.javaway.spend_sense.settings.child.sync.compose.SyncContract.UiEvent
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SyncComponent(
    context: ComponentContext,
    private val settingsManager: SettingsManager,
    private val categoriesRepository: CategoriesRepository,
    private val eventsRepository: EventsRepository,
    private val api: AppApi,
    private val onOutput : (Output) -> Unit
) : ComponentContext by context, StateHolder<State>, UiEventHandler<UiEvent> {

    private val _state = MutableStateFlow(State(email = settingsManager.email))
    override val state = _state.asStateFlow()

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Logout -> logout()
            is UiEvent.Sync -> sync()
        }
    }

    private fun sync() = componentScope.launch(
        CoroutineExceptionHandler { _, t ->
            appLog(t.stackTraceToString())
            onOutput(Output.Error(t.message.orEmpty()))
        }
    ) {
        _state.updateState { copy(isLoading = true) }
        delay(3000)
        syncCategories()
        syncEvens()
        onOutput(Output.DataSynced)
        _state.updateState { copy(isLoading = false) }
    }

    private fun logout() {
        settingsManager.email = ""
        settingsManager.token = ""
    }

    private suspend fun syncCategories() {
        val apiCategories = categoriesRepository.getAll().map { it.toApi() }
        val categoriesSyncResponse = api.syncCategories(apiCategories)
        if (categoriesSyncResponse.status.isSuccess()) {
            val remoteCategories = categoriesSyncResponse.body<List<CategoryApi>>()
            categoriesRepository.insertAll(remoteCategories.map(CategoryApi::toEntity))
        }
    }

    private suspend fun syncEvens() {
        val apiEvents = eventsRepository.getAll().map { it.toApi() }
        val eventsSyncResponse = api.syncEvents(apiEvents)
        if (eventsSyncResponse.status.isSuccess()) {
            val remoteEvents = eventsSyncResponse.body<List<SpendEventApi>>()
            eventsRepository.insertAll(remoteEvents.map { it.toEntity() })
        }
    }

    class Factory(
        private val settingsManager: SettingsManager,
        private val categoriesRepository: CategoriesRepository,
        private val eventsRepository: EventsRepository,
        private val api: AppApi,
    ) {
        fun create(
            context: ComponentContext,
            onOutput : (Output) -> Unit
        )  = SyncComponent(
            context, settingsManager, categoriesRepository, eventsRepository, api, onOutput
        )
    }
}