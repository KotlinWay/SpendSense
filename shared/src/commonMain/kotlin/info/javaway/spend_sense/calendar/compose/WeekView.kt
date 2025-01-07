package info.javaway.spend_sense.calendar.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.calendar.model.CalendarDay
import info.javaway.spend_sense.calendar.model.CalendarWeek


@Composable
internal fun WeekView(
    week: CalendarWeek,
    colors: CalendarColors,
    selectDayListener: (CalendarDay) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        week.days.forEach { calendarDay ->
            CalendarDayView(
                calendarDay = calendarDay,
                colors = colors,
                selectDayListener = selectDayListener
            )
        }
    }
}

