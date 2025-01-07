package info.javaway.spend_sense

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.root.RootComponent
import info.javaway.spend_sense.root.compose.RootScreen
import platform.UIKit.UIViewController

fun rootViewController(root: RootComponent) = ComposeUIViewController(
    configure = { enforceStrictPlistSanityCheck = false }
) {
    RootScreen(component = root)
}

fun getRootFactory(): RootComponent.Factory = getKoinInstance()