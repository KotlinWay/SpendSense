package info.javaway.spend_sense

import androidx.compose.ui.window.ComposeUIViewController
import info.javaway.spend_sense.root.compose.RootScreen
import info.javaway.spend_sense.root.RootViewModel

fun MainViewController()  = ComposeUIViewController { RootScreen(RootViewModel()) }