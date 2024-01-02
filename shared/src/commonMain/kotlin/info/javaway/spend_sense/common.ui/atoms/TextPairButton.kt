package info.javaway.spend_sense.common.ui.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.extensions.fromHex

@Composable
fun TextPairButton(
    title: String,
    subtitle: String? = null,
    buttonTitle: String,
    colorHex: String? = null,
    onClick: () -> Unit
) {

    val color = colorHex?.let { Color.fromHex(it) } ?: AppThemeProvider.colors.accent

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
            Text(title, fontSize = 20.sp, color = AppThemeProvider.colors.onSurface)
            if(subtitle != null){
                Text(
                    text = subtitle,
                    fontSize = 18.sp,
                    color = AppThemeProvider.colors.onSurface.copy(0.8f),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        OutlinedButton(
            onClick = onClick,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = AppThemeProvider.colors.background,
                contentColor = color
            ),
            border = BorderStroke(1.dp, color)
        ){
            Text(buttonTitle, color = color)
        }
    }

}