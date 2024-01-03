package info.javaway.spend_sense.common.ui.calendar.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDate.Companion.initValue() = LocalDate(1970, 1, 1)
