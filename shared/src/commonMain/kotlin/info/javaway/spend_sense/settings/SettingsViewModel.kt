package info.javaway.spend_sense.settings

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.model.CategoryApi
import info.javaway.spend_sense.categories.model.toApi
import info.javaway.spend_sense.categories.model.toEntity
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.model.SpendEventApi
import info.javaway.spend_sense.events.model.toApi
import info.javaway.spend_sense.events.model.toEntity
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.settings.SettingsContract.Event
import info.javaway.spend_sense.settings.SettingsContract.State
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val deviceInfo: DeviceInfo,
    private val settingsManager: SettingsManager,
    private val categoriesRepository: CategoriesRepository,
    private val eventsRepository: EventsRepository,
    private val api: AppApi
) : BaseViewModel<State, Event>() {

    init {
        bindToEmail()
        bindToTheme()
        bindToToken()
        initDeviceInfo()
    }

    fun switchTheme(isDark: Boolean) {
        settingsManager.themeIsDark = isDark
    }

    fun sync() = viewModelScope.launch(
        CoroutineExceptionHandler { _, t ->
            appLog(t.stackTraceToString())
            pushEvent(Event.Error(t.message.orEmpty()))
        }
    ) {
        updateState { copy(isLoading = true) }
        delay(3000)
        syncCategories()
        syncEvens()
        pushEvent(Event.DataSynced)
        updateState { copy(isLoading = false) }
    }

    fun logout() {
        settingsManager.email = ""
        settingsManager.token = ""
    }

    //*********** region private *************

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

    private fun bindToEmail() {
        settingsManager.emailFlow.onEach { email ->
            updateState { copy(email = email) }
        }.launchIn(viewModelScope)
    }

    private fun bindToToken() {
        settingsManager.tokenFlow.onEach { token ->
            updateState { copy(isAuth = token.isNotBlank()) }
        }.launchIn(viewModelScope)
    }

    private fun initDeviceInfo() {
        updateState {
            copy(info = deviceInfo.getSummary())
        }
    }

    private fun bindToTheme() {
        settingsManager.themeIsDarkFlow.onEach {
            updateState { copy(themeIsDark = it) }
        }.launchIn(viewModelScope)
    }

    override fun initialState() = State.NONE

    //endregion

}