import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import info.javaway.spend_sense.di.initKoin
import info.javaway.spend_sense.extensions.initLogs
import info.javaway.spend_sense.root.compose.RootScreen

fun main() {

    initKoin()
    initLogs()

    application {

        val state = rememberWindowState().apply { size = DpSize(800.dp, 600.dp) }
        Window(
            onCloseRequest = { exitApplication() },
            state = state,
            title = "SpendSense"
        ) {
            RootScreen()
        }
    }
}

