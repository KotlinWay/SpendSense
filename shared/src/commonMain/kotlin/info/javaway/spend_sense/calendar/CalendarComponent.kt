package info.javaway.spend_sense.calendar

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.navigate
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.router.pages.selectNext
import com.arkivanov.decompose.router.pages.selectPrev
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.base.StateHolder
import info.javaway.spend_sense.calendar.CalendarContract.*
import info.javaway.spend_sense.calendar.model.CalendarDay
import info.javaway.spend_sense.calendar.model.CalendarLabel
import info.javaway.spend_sense.calendar.model.CalendarMonth
import info.javaway.spend_sense.calendar.month_page.MonthPageComponent
import info.javaway.spend_sense.calendar.month_page.MonthPageContract
import info.javaway.spend_sense.extensions.now
import info.javaway.spend_sense.extensions.updateState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.serialization.Serializable

class CalendarComponent(
    context: ComponentContext,
    labels: Map<CalendarMonth, List<CalendarLabel>> = emptyMap(),
    private val onOutput: (Output) -> Unit
) : ComponentContext by context, StateHolder<State> {

    private val _state = MutableStateFlow(State.NONE.copy(labels = labels))
    override val state = _state

    private val navigation = PagesNavigation<MonthPageConfig>()

    val pages: Value<ChildPages<MonthPageConfig, MonthPageComponent>> = childPages(
        source = navigation,
        serializer = MonthPageConfig.serializer(),
        initialPages = ::buildInitPage,
        childFactory = ::createChildPages
    )

    fun selectPage(index: Int) = when (index) {
        pages.value.items.size - 1 -> inflateMonths(false)
        0 -> inflateMonths(true)
        else -> navigation.select(index = index)
    }

    fun onEvent(event: UiEvent) = when (event) {
        is UiEvent.NextMonth -> navigation.selectNext()
        is UiEvent.PrevMonth -> navigation.selectPrev()
        is UiEvent.UpdateLabels -> updateLabels(event.labels)
        is UiEvent.UpdateYear -> updateYear(event.year)
    }

    private fun updateLabels(labels: List<CalendarLabel>) {
        _state.updateState {
            copy(labels = labels.groupBy { CalendarMonth.fromDate(it.localDate) })
        }
        updateMonthsInPager()
    }

    private fun updateYear(year: Int) {
        val selectedMonth = state.value.selectedMonth.copy(year = year)
        val selectedDate = LocalDate(selectedMonth.year, selectedMonth.month, 1)
        val months = mutableListOf<CalendarMonth>()
        appendMonths(selectedDate, months, true)
        months.add(selectedMonth)
        appendMonths(selectedDate, months, false)
        _state.updateState { copy(months = months) }
        navigation.navigate {
            it.copy(
                items = months.map { month ->
                    MonthPageConfig(
                        selectedDay = state.value.selectedDay,
                        month = month,
                        labels = state.value.labels[month].orEmpty()
                    )
                },
                selectedIndex = months.indexOf(selectedMonth)
            )
        }
    }

    private fun inflateMonths(toHead: Boolean) {
        val months = state.value.months.toMutableList()
        val month = if (toHead) months.first() else months.last()
        val date = LocalDate(month.year, month.month.number, 1)
        appendMonths(date, months, toHead)
        state.updateState { copy(months = months) }
        navigation.navigate {
            it.copy(items = months.map { month ->
                MonthPageConfig(
                    selectedDay = state.value.selectedDay,
                    month = month,
                    labels = state.value.labels[month].orEmpty()
                )
            })
        }
    }

    private fun buildInitPage(): Pages<MonthPageConfig> {
        val currentDate = LocalDate.now()
        val months = mutableListOf<CalendarMonth>()
        val currentMonth = CalendarMonth(currentDate.year, currentDate.month)
        appendMonths(currentDate, months, true)
        months.add(currentMonth)
        appendMonths(currentDate, months, false)
        _state.updateState { copy(months = months) }

        return Pages(
            items = months.map {
                MonthPageConfig(
                    selectedDay = null,
                    month = it,
                    labels = state.value.labels[it].orEmpty()
                )
            },
            selectedIndex = months.indexOf(currentMonth)
        )
    }

    private fun appendMonths(
        currentDate: LocalDate,
        months: MutableList<CalendarMonth>,
        toHead: Boolean,
    ) {
        repeat(MONTH_PREFETCH_COUNT) { count ->
            val actualCount = if (toHead) (count + 1) * -1 else count + 1
            currentDate.plus(actualCount, DateTimeUnit.MONTH).let { date ->
                CalendarMonth(year = date.year, month = date.month)
            }.run {
                if (toHead) months.add(0, this) else months.add(this)
            }
        }
    }

    private fun createChildPages(config: MonthPageConfig, context: ComponentContext) =
        MonthPageComponent(
            context = context,
            selectedDay = config.selectedDay,
            month = config.month,
            labels = config.labels,
            firstDayIsMonday = state.value.firstDayIsMonday,
            onOutput = ::onMonthOutput
        )

    private fun onMonthOutput(output: MonthPageContract.Output) {
        when (output) {
            is MonthPageContract.Output.SelectDay -> {
                _state.updateState { copy(selectedDay = output.day) }
                updateMonthsInPager()
                onOutput(Output.SelectDay(output.day))
            }

            is MonthPageContract.Output.SelectMonth -> _state.updateState {
                copy(selectedMonth = output.month)
            }
        }
    }

    private fun updateMonthsInPager() {
        navigation.navigate {
            it.copy(items = state.value.months.map { month ->
                MonthPageConfig(
                    selectedDay = state.value.selectedDay,
                    month = month,
                    labels = state.value.labels[month].orEmpty()
                )
            })
        }
    }

    @Serializable
    data class MonthPageConfig(
        val selectedDay: CalendarDay?,
        val month: CalendarMonth,
        val labels: List<CalendarLabel>
    )
}

const val MONTH_PREFETCH_COUNT = 5