package info.javaway.spend_sense.settings

import info.javaway.spend_sense.base.BaseViewState

class SettingsContract {
    data class State(
        val deviceInfo: String,
        val themeIsDark: Boolean
    ) : BaseViewState {
        companion object {
            val NONE = State(
                deviceInfo = "",
                themeIsDark = false
            )
        }
    }
}