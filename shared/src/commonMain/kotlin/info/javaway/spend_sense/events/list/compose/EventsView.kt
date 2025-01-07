package info.javaway.spend_sense.events.list.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.spend_sense.calendar.compose.CalendarColors
import info.javaway.spend_sense.common.ui.atoms.FAB
import info.javaway.spend_sense.common.ui.atoms.RootBox
import info.javaway.spend_sense.calendar.compose.CalendarViewUi
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.events.create.compose.CreateEventView
import info.javaway.spend_sense.events.list.EventsComponent
import info.javaway.spend_sense.events.list.EventsContract
import info.javaway.spend_sense.events.list.EventsContract.UiEvent.ShowCreateEventDialog

@Composable
fun EventsScreen(
    component: EventsComponent
) {

    val state by component.state.collectAsState()
    val slots by component.slots.subscribeAsState()
    val onEvent = component::onEvent

    RootBox {
        Column {
            CalendarViewUi(
                component = component.calendarComponent,
                colors = CalendarColors.default.copy(
                    colorSurface = AppThemeProvider.colors.surface,
                    colorOnSurface = AppThemeProvider.colors.onSurface,
                    colorAccent = AppThemeProvider.colors.accent
                )
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.eventsByDay) { eventUI ->
                    SpendEventItem(eventUI)
                }
            }
        }

        FAB { onEvent(ShowCreateEventDialog) }
    }

    when (val child = slots.child?.instance) {
        is EventsContract.Child.CreateEvent -> CreateEventView(child.component)
        null -> Unit
    }
}












