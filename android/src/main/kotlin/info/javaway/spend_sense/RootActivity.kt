package info.javaway.spend_sense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class RootActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SayHelloFromCommon()
        }
    }
}