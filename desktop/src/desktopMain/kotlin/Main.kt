import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import info.javaway.spend_sense.SayHelloFromCommon
import info.javaway.spend_sense.root.RootScreen
import info.javaway.spend_sense.sayHello

fun main() {
    sayHello()

    application {

        val state = rememberWindowState().apply { size = DpSize(200.dp, 200.dp) }
        Window(
            onCloseRequest = { exitApplication() },
            state = state,
            title = "SpendSense"
        ) {
            RootScreen()
        }
    }
}

