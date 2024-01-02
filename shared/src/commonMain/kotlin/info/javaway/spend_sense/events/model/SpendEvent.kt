package info.javaway.spend_sense.events.model

import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class SpendEvent(
    val id: String,
    val categoryId: String,
    val title: String,
    val cost: Double,
    val date: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        val NONE = SpendEvent(
            id = "",
            categoryId = "",
            title = "",
            cost = 0.0,
            date = LocalDate.now(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        fun getStubs() = List(20) { index ->
            NONE.copy(
                id = index.toString(),
                title = "event $index",
                date = Clock.System.now()
                    .plus(index, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
            )
        }
    }
}
