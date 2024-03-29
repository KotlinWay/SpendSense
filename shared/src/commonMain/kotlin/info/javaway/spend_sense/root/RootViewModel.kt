package info.javaway.spend_sense.root

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.root.model.AppTab
import info.javaway.spend_sense.root.model.RootContract.RootState
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RootViewModel(
    private val settingsManager: SettingsManager
) : BaseViewModel<RootState, Nothing>() {

    init {
        settingsManager.themeIsDarkFlow.onEach {
            updateState { copy(themeIsDark = it) }
        }.launchIn(viewModelScope)
    }
    override fun initialState() = RootState.NONE

    fun handleClickOnTab(appTab: AppTab) = updateState { copy(selectedTab = appTab) }
}