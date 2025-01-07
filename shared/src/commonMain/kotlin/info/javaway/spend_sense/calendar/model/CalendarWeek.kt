package info.javaway.spend_sense.calendar.model

data class CalendarWeek(
    val days: List<CalendarDay>
){
    companion object{
        const val MAX_WEEKS = 6
        private const val countDays = 7
        val EMPTY_WEEK = CalendarWeek(days = List(countDays) { CalendarDay.NONE.copy(isStub = true) })
        val PLACEHOLDER = List(MAX_WEEKS) { EMPTY_WEEK }
    }
}

