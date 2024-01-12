package info.javaway.spend_sense.storage

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsManager(private val settings: Settings) {

    private val THEME_KEY = "THEME_KEY"
    private val TOKEN_KEY = "TOKEN_KEY"
    private val EMAIL_KEY = "EMAIL_KEY"

    //your local machine
    val serverUrl = "http://192.168.1.194:1337"


    private val _themeIsDarkFlow = MutableStateFlow(themeIsDark)
    val themeIsDarkFlow = _themeIsDarkFlow.asStateFlow()
    var themeIsDark: Boolean
        set(value) {
            _themeIsDarkFlow.update { value }
            settings.putBoolean(THEME_KEY, value)
        }
        get() = settings.getBoolean(THEME_KEY, true)

    private val _tokenFlow = MutableStateFlow(token)
    val tokenFlow = _tokenFlow.asStateFlow()
    var token: String
        set(value) {
            settings.putString(TOKEN_KEY, value)
            _tokenFlow.update { value }
        }
        get() = settings.getString(TOKEN_KEY, "")

    private val _emailFlow = MutableStateFlow(email)
    val emailFlow = _emailFlow.asStateFlow()
    var email: String
        set(value) {
            settings.putString(EMAIL_KEY, value)
            _emailFlow.update { value }
        }
        get() = settings.getString(EMAIL_KEY, "")
}