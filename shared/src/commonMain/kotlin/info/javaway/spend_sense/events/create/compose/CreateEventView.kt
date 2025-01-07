package info.javaway.spend_sense.events.create.compose


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.spend_sense.calendar.compose.CalendarColors
import info.javaway.spend_sense.calendar.compose.CalendarDialogUi
import info.javaway.spend_sense.categories.picker.CategoryPickerUi
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppModalBottomSheetNative
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.TextPairButton
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.events.create.CreateEventComponent
import info.javaway.spend_sense.events.create.CreateEventContract.Child
import info.javaway.spend_sense.events.create.CreateEventContract.State
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.ChangeCost
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.ChangeNote
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.ChangeTitle
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.ClickOnCategories
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.ClickOnDate
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.Dismiss
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.DismissCalendar
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.DismissCategories
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.Finish
import info.javaway.spend_sense.events.create.CreateEventContract.UiEvent.SelectCategory
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.category
import spendsense.shared.generated.resources.cost
import spendsense.shared.generated.resources.date
import spendsense.shared.generated.resources.empty_category
import spendsense.shared.generated.resources.save
import spendsense.shared.generated.resources.spend_to

@Composable
fun CreateEventView(
    component: CreateEventComponent,
) {

    val state by component.state.collectAsState()
    val slots by component.slots.subscribeAsState()
    val onEvent = component::onEvent

    CreateEventViewContent(state, onEvent)

    when (val child = slots.child?.instance) {
        is Child.Calendar -> CalendarDialogUi(
            component = child.component,
            colors = CalendarColors.default.copy(
                colorSurface = AppThemeProvider.colors.surface,
                colorOnSurface = AppThemeProvider.colors.onSurface,
                colorAccent = AppThemeProvider.colors.accent
            ),
            onDismiss = { onEvent(DismissCalendar) }
        )

        is Child.CategoriesPicker -> CategoryPickerUi(
            categories = state.categories,
            onDismiss = { onEvent(DismissCategories) },
            selectCategory = { onEvent(SelectCategory(it)) }
        )

        null -> Unit
    }
}

@Composable
fun CreateEventViewContent(
    state: State,
    onEvent: (UiEvent) -> Unit
) {

    AppModalBottomSheetNative(onDismissRequest = { onEvent(Dismiss) }) {
        Column (
            modifier = Modifier.padding(8.dp).padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextPairButton(
                title = stringResource(Res.string.category),
                buttonTitle = state.category.title.ifEmpty { stringResource(Res.string.empty_category) },
                colorHex = state.category.colorHex.takeIf { it.isNotEmpty() }
            ) { onEvent(ClickOnCategories) }

            TextPairButton(
                title = stringResource(Res.string.date),
                buttonTitle = state.date.toString()
            ) { onEvent(ClickOnDate) }

            AppTextField(
                value = state.title,
                placeholder = stringResource(Res.string.spend_to),
                modifier = Modifier.fillMaxWidth()
            ) { onEvent(ChangeTitle(it)) }

            AppTextField(
                value = state.note,
                placeholder = "note",
                modifier = Modifier.fillMaxWidth()
            ) { onEvent(ChangeNote(it)) }

            AppTextField(
                value = state.cost.toString(),
                placeholder = stringResource(Res.string.cost),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            ) { onEvent(ChangeCost(it)) }

            AppButton(stringResource(Res.string.save)) { onEvent(Finish) }
        }
    }
}









