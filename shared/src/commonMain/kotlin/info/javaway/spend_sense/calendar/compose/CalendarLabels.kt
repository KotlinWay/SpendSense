package info.javaway.spend_sense.calendar.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import info.javaway.spend_sense.calendar.extensions.fromSunday
import kotlinx.datetime.DayOfWeek

@Composable
internal fun CalendarDaysLabels(
    firstDayIsMonday: Boolean,
    colors: CalendarColors
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DayOfWeek.entries.toTypedArray()
            .let { if (firstDayIsMonday) it.toList() else it.fromSunday() }
            .forEach { dayOfWeek ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        dayOfWeek.name.take(2),
                        style = TextStyle(
                            color = colors.colorOnSurface.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
    }
}
