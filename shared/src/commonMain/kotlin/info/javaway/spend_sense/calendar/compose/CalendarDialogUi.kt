package info.javaway.spend_sense.calendar.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import info.javaway.spend_sense.calendar.CalendarComponent

@Composable
fun CalendarDialogUi(
    component: CalendarComponent,
    colors: CalendarColors = CalendarColors.default,
    onDismiss: () -> Unit
) = Dialog(
    properties = DialogProperties(usePlatformDefaultWidth = false),
    onDismissRequest = onDismiss
) {
    CalendarViewUi(component, colors)
}
