package info.javaway.spend_sense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import info.javaway.spend_sense.root.RootScreen
import info.javaway.spend_sense.root.RootViewModel

class RootActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootScreen(RootViewModel())
        }
    }
}