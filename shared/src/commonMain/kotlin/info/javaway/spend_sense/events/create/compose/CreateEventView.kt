package info.javaway.spend_sense.events.create.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.icerock.moko.resources.compose.stringResource
import info.javaway.spend_sense.MR
import info.javaway.spend_sense.categories.list.compose.CategoriesListView
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.BottomModalContainer
import info.javaway.spend_sense.common.ui.atoms.TextPairButton
import info.javaway.spend_sense.common.ui.calendar.compose.CalendarColors
import info.javaway.spend_sense.common.ui.calendar.compose.DatePickerView
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.di.DatePickerFactoryQualifier
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.events.create.CreateEventViewModel
import info.javaway.spend_sense.events.model.SpendEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CreateEventView(
    isExpand: Boolean,
    selectedDay: CalendarDay?,
    viewModel: CreateEventViewModel,
    createListener: (SpendEvent) -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showDateDialog by remember { mutableStateOf(false) }
    var showCategoriesDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isExpand) {
        if (isExpand) {
            viewModel.selectDate(selectedDay?.date)
        } else {
            viewModel.resetState()
        }

        viewModel.events.onEach { event ->
            when (event) {
                is CreateEventViewModel.Event.Finish -> createListener(event.spendEvent)
            }
        }.launchIn(this)
    }


    BottomModalContainer {
        TextPairButton(
            title = stringResource(MR.strings.category),
            buttonTitle = state.category.title.ifEmpty { stringResource(MR.strings.empty_category) },
            colorHex = state.category.colorHex.takeIf { it.isNotEmpty() }
        ) { showCategoriesDialog = true }

        TextPairButton(
            title = stringResource(MR.strings.date),
            buttonTitle = state.date.toString()
        ) { showDateDialog = true }

        AppTextField(
            value = state.title,
            placeholder = stringResource(MR.strings.spend_to),
            modifier = Modifier.fillMaxWidth()
        ) { viewModel.changeTitle(it) }

        AppTextField(
            value = state.note,
            placeholder = "note",
            modifier = Modifier.fillMaxWidth()
        ) { viewModel.changeNote(it) }

        AppTextField(
            value = state.cost.toString(),
            placeholder = stringResource(MR.strings.cost),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ) { viewModel.changeCost(it) }

        AppButton(stringResource(MR.strings.save)) {
            viewModel.finish()
        }
    }


    if (showCategoriesDialog) {
        Dialog(
            onDismissRequest = { showCategoriesDialog = false }
        ) {
            CategoriesListView(
                getKoinInstance(),
                modifier = Modifier.background(
                    AppThemeProvider.colors.surface,
                    RoundedCornerShape(16.dp)
                )
            ) { category ->
                showCategoriesDialog = false
                viewModel.selectCategory(category)
            }
        }
    }

    if (showDateDialog) {
        Dialog(onDismissRequest = { showDateDialog = false }) {
            DatePickerView(
                viewModel = getKoinInstance(DatePickerFactoryQualifier),
                colors = CalendarColors.default.copy(
                    colorAccent = AppThemeProvider.colors.accent,
                    colorOnSurface = AppThemeProvider.colors.onSurface,
                    colorSurface = AppThemeProvider.colors.surface
                )
            ){ day ->
                showDateDialog = false
                viewModel.selectDate(day.date)
            }
        }
    }

}









