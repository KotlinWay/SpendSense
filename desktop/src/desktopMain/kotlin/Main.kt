import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.di.initKoin
import info.javaway.spend_sense.extensions.initLogs
import info.javaway.spend_sense.root.RootComponent
import info.javaway.spend_sense.root.compose.RootScreen

fun main() {

    initKoin()
    initLogs()

    val lifecycle = LifecycleRegistry()
    val rootFactory = getKoinInstance<RootComponent.Factory>()
    val root = runOnUiThread { rootFactory.create(DefaultComponentContext(lifecycle = lifecycle)) }

    application {
        val state = rememberWindowState().apply { size = DpSize(800.dp, 600.dp) }

        Window(
            onCloseRequest = { exitApplication() },
            state = state,
            title = "SpendSense"
        ) {

            LifecycleController(
                lifecycleRegistry = lifecycle,
                windowState = state,
                windowInfo = LocalWindowInfo.current,
            )

            RootScreen(root)
        }
    }
}

