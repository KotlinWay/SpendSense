package info.javaway.spend_sense.root

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.root.RootContract.State
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RootViewModel : BaseViewModel<State, Nothing>() {

    init {
        SettingsManager.themeIsDarkFlow.onEach { isDark ->
            updateState { copy(themeIsDark = isDark) }
        }.launchIn(viewModelScope)
    }

    override fun initialState() = State.NONE
}