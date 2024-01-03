package info.javaway.spend_sense.common.ui.calendar.model

data class CalendarWeek(
    val days: List<CalendarDay>
){
    companion object{
        private const val countWeeks = 6
        private const val countDays = 7
        val PLACEHOLDER = List(countWeeks) { CalendarWeek(days = List(countDays) { CalendarDay.NONE.copy(isStub = false) }) }
    }
}

