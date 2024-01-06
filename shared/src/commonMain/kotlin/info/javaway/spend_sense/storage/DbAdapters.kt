package info.javaway.spend_sense.storage

import app.cash.sqldelight.ColumnAdapter
import db.categories.CategoryDb
import db.events.EventDb
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime

object DbAdapters {
    val categoryDbAdapter = CategoryDb.Adapter(
        LocalDateTimeAdapter, LocalDateTimeAdapter
    )
    val eventDbAdapter = EventDb.Adapter(
        LocalDateAdapter, LocalDateTimeAdapter, LocalDateTimeAdapter
    )
}

object LocalDateTimeAdapter : ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String) = databaseValue.toLocalDateTime()

    override fun encode(value: LocalDateTime) = value.toString()
}

object LocalDateAdapter : ColumnAdapter<LocalDate, String> {
    override fun decode(databaseValue: String) = databaseValue.toLocalDate()

    override fun encode(value: LocalDate) = value.toString()

}