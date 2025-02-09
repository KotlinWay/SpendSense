package info.javaway.spend_sense

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import info.javaway.spend_sense.root.RootComponent
import info.javaway.spend_sense.root.compose.RootScreen
import info.javaway.spend_sense.storage.SettingsManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RootActivity: ComponentActivity(), KoinComponent{

    private val rootFactory by inject<RootComponent.Factory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = rootFactory.create(defaultComponentContext())
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        setContent { RootScreen(root) }
    }
}