package info.javaway.spend_sense.settings.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.common.ui.atoms.AppCard
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider

@Composable
fun DeviceInfoView(info: String) {
    AppCard {
        Text(
            info,
            color = AppThemeProvider.colors.onBackground,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
}