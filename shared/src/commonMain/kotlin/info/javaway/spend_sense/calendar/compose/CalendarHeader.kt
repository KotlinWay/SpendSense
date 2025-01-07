package info.javaway.spend_sense.calendar.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.javaway.spend_sense.calendar.model.CalendarMonth

@Composable
internal fun CalendarHeader(
    calendarMonth: CalendarMonth,
    colors: CalendarColors,
    prevMonthListener: () -> Unit,
    nextMonthListener: () -> Unit,
    yearSelectListener: (Int) -> Unit,
) {
    var selectYearDialogIsShown by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            calendarMonth.year.toString(),
            style = TextStyle(
                color = colors.colorOnSurface,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.clickable { selectYearDialogIsShown = true }
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            calendarMonth.month.name.lowercase().capitalize(Locale.current),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            style = TextStyle(
                color = colors.colorOnSurface,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
        )
        Image(
            imageVector = Icons.Rounded.ChevronLeft,
            contentDescription = "back",
            colorFilter = ColorFilter.tint(colors.colorAccent),
            modifier = Modifier
                .padding(end = 24.dp)
                .size(38.dp)
                .clickable {
                    prevMonthListener()
                }
        )
        Image(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = "next",
            colorFilter = ColorFilter.tint(colors.colorAccent),
            modifier = Modifier
                .size(38.dp)
                .clickable { nextMonthListener() }
        )

        if (selectYearDialogIsShown) {
            YearPicker(
                initialYear = calendarMonth.year,
                onDismissRequest = { selectYearDialogIsShown = false },
                onYearSelectListener = yearSelectListener,
                colors = colors
            )
        }
    }
}