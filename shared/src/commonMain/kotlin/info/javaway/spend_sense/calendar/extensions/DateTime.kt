package info.javaway.spend_sense.calendar.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val LocalDate.Companion.initValue: LocalDate
    get() = LocalDate(1970, 1, 1)
