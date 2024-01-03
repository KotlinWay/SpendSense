package info.javaway.spend_sense.common.ui.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RootBox(content: @Composable BoxScope.() -> Unit){
    Box(modifier = Modifier.fillMaxSize().padding(bottom = 70.dp)){
        content()
    }
}