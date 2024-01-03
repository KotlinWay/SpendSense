package info.javaway.spend_sense.events

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.common.ui.calendar.compose.CalendarColors
import info.javaway.spend_sense.common.ui.calendar.compose.DatePickerView
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.di.getKoinInstance

@Composable
fun BoxScope.EventsScreen() {
    DatePickerView(
        viewModel = getKoinInstance(),
        colors = CalendarColors.default.copy(
            colorSurface = AppThemeProvider.colors.surface,
            colorOnSurface = AppThemeProvider.colors.onSurface,
            colorAccent = AppThemeProvider.colors.accent
        ),
        firstDayIsMonday = AppThemeProvider.appPrefs.firstDayIsMonday,
        labels = emptyList(),
        selectDayListener = { day -> }
    )
}