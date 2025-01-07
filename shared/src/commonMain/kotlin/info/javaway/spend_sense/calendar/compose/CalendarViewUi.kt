package info.javaway.spend_sense.calendar.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import info.javaway.spend_sense.calendar.CalendarComponent
import info.javaway.spend_sense.calendar.CalendarContract.UiEvent.NextMonth
import info.javaway.spend_sense.calendar.CalendarContract.UiEvent.PrevMonth
import info.javaway.spend_sense.calendar.CalendarContract.UiEvent.UpdateYear
import info.javaway.spend_sense.calendar.month_page.MonthPageUi

@Composable
fun CalendarViewUi(
    component: CalendarComponent,
    colors: CalendarColors = CalendarColors.default,
) {

    val state by component.state.collectAsState()
    val onEvent = component::onEvent

    CompositionLocalProvider(
        LocalCalendarColors provides colors
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(colors.colorSurface, shape = RoundedCornerShape(16.dp))
        ) {
            CalendarHeader(
                calendarMonth = state.selectedMonth,
                colors = colors,
                prevMonthListener = { onEvent(PrevMonth) },
                nextMonthListener = { onEvent(NextMonth) },
                yearSelectListener = { year -> onEvent(UpdateYear(year)) }
            )

            Divider(color = colors.colorOnSurface.copy(alpha = 0.5f))
            ChildPages(
                pages = component.pages,
                onPageSelected = component::selectPage,
                scrollAnimation = PagesScrollAnimation.Default,
                pageContent = { _, page -> MonthPageUi(page, colors) }
            )
        }
    }
}