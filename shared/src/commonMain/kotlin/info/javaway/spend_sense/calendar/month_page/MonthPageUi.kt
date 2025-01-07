package info.javaway.spend_sense.calendar.month_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import info.javaway.spend_sense.calendar.compose.CalendarColors
import info.javaway.spend_sense.calendar.compose.CalendarDaysLabels
import info.javaway.spend_sense.calendar.compose.WeekView
import info.javaway.spend_sense.calendar.month_page.MonthPageContract.*

@Composable
fun MonthPageUi(
    component: MonthPageComponent,
    colors: CalendarColors
) {

    val state by component.state.collectAsState()

    Column {
        CalendarDaysLabels(firstDayIsMonday = state.firstDayIsMonday, colors = colors)
        state.fullSizeWeeks.forEach { week ->
            WeekView(week = week, colors = colors) {
                day -> component.onEvent(UiEvent.SelectDay(day))
            }
        }
    }
}