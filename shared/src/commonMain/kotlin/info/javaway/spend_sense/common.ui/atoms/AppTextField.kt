package info.javaway.spend_sense.common.ui.atoms

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider

@Composable
fun AppTextField(
    value: String,
    placeholder: String = "",
    fontSize: TextUnit = 20.sp,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = TextStyle(
            color = AppThemeProvider.colors.onSurface,
            fontSize = fontSize
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            cursorColor = AppThemeProvider.colors.onSurface.copy(0.5f),
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        placeholder = { Text(placeholder, color = AppThemeProvider.colors.onSurface.copy(0.5f)) },
        keyboardOptions = keyboardOptions
    )
}