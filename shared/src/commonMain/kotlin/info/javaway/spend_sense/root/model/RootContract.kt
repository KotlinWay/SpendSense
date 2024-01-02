package info.javaway.spend_sense.root.model

import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.common.ui.theme.AppPrefs

class RootContract {

    data class RootState(
        val themeIsDark: Boolean,
        val firstDayIsMonday: Boolean,
        val selectedTab: AppTab
    ): BaseViewState{

        val appPrefs: AppPrefs
            get() = AppPrefs(firstDayIsMonday = firstDayIsMonday)

        companion object {
            val NONE = RootState(
                themeIsDark = false,
                firstDayIsMonday = true,
                selectedTab = AppTab.Events
            )
        }
    }

}