package info.javaway.spend_sense.storage

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual class AppSettings {
    actual val settings: Settings = PreferencesSettings(Preferences.userRoot())
}