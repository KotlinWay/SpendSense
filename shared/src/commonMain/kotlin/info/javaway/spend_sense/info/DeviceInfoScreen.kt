package info.javaway.spend_sense.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.platform.DeviceInfo

@Composable
fun DeviceInfoScreen() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(DeviceInfo().getSummary())
    }
}