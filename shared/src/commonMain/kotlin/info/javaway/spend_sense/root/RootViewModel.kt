package info.javaway.spend_sense.root

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.root.RootContract.*
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RootViewModel : BaseViewModel<RootState, Nothing>() {

    init {
        SettingsManager.themeIsDarkFlow.onEach {
            updateState { copy(themeIsDark = it) }
        }.launchIn(viewModelScope)


    }
    override fun initialState() = RootState.NONE
}