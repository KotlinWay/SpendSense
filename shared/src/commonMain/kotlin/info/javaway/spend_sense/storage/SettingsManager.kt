package info.javaway.spend_sense.storage

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

expect class AppSettings constructor(){
    val settings: Settings
}

object SettingsManager {

    private val appSettings: AppSettings = AppSettings()

    private const val THEME_KEY = "THEME_KEY"

    private val _themeIsDarkFlow = MutableStateFlow(themeIsDark)
    val themeIsDarkFlow = _themeIsDarkFlow.asStateFlow()
    var themeIsDark: Boolean
        set(value) {
            _themeIsDarkFlow.update { value }
            appSettings.settings.putBoolean(THEME_KEY, value)
        }
        get() = appSettings.settings.getBoolean(THEME_KEY, true)
}