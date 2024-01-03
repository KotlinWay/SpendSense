package info.javaway.spend_sense.common.ui.calendar.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.common.ui.calendar.model.CalendarLabel
import info.javaway.spend_sense.extensions.fromHex

@Composable
fun RowScope.CalendarDayView(
    calendarDay: CalendarDay,
    colors: CalendarColors,
    selectDayListener: (CalendarDay) -> Unit,
) {

    if (calendarDay.isStub) {
        Spacer(modifier = Modifier.weight(1f))
    } else {
        DayContainer(
            calendarDay = calendarDay,
            selectDayListener = selectDayListener
        ) {
            DayText(calendarDay, colors)
            CurrentDayLabel(calendarDay, colors)
            GradientLabels(calendarDay.labels)
        }
    }
}

@Composable
fun RowScope.DayContainer(
    calendarDay: CalendarDay,
    selectDayListener: (CalendarDay) -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .let {
                if (calendarDay.selectable) {
                    it.clickable {
                        selectDayListener(calendarDay)
                    }
                } else it
            }
    ) {
        content()
    }
}

@Composable
fun BoxScope.DayText(
    calendarDay: CalendarDay,
    colors: CalendarColors
) {

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(2.dp)
            .border(
                1.dp,
                colors.colorAccent.copy(if (calendarDay.isSelected) 1f else 0f),
                RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = calendarDay.number.toString(),
            style = TextStyle(
                color = if (calendarDay.isToday) colors.colorAccent else colors.colorOnSurface.copy(
                    0.6f
                ),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun BoxScope.CurrentDayLabel(
    calendarDay: CalendarDay,
    colors: CalendarColors
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .height(2.dp)
            .align(Alignment.BottomCenter)
            .background(
                colors.colorOnSurface.copy(if (calendarDay.isToday) 1f else 0f)
            )
    ) {}
}


@Composable
fun GradientLabels(labels: List<CalendarLabel>){
    if (labels.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(CircleShape)
                .border(
                    4.dp,
                    brush = Brush.linearGradient(
                        colors = labels.mapToColors(),
                    ),
                    shape = CircleShape
                )

        ) { }
    }
}

@Composable
fun List<CalendarLabel>.mapToColors() = this.map {
    it.colorHex?.takeIf { it.isNotEmpty() }?.let { colorHex ->
        Color.fromHex(colorHex)
    } ?: LocalCalendarColors.current.colorAccent
}
    .toSet()
    .toList()
    .let { if (it.size == 1) { it + it } else it }