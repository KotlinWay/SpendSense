package info.javaway.spend_sense.root

import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.common.ui.AppPrefs

class RootContract {

    data class RootState(
        val themeIsDark: Boolean,
        val firstDayIsMonday: Boolean
    ): BaseViewState{

        val appPrefs: AppPrefs
            get() = AppPrefs(firstDayIsMonday = firstDayIsMonday)

        companion object {
            val NONE = RootState(
                themeIsDark = false,
                firstDayIsMonday = true
            )
        }
    }

}