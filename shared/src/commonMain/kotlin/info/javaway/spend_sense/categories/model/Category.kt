package info.javaway.spend_sense.categories.model

import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Category(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updateAt: LocalDateTime,
    val colorHex: String
){
    companion object {
        val NONE = Category(
            id = "NONE_CATEGORY",
            title = "",
            description = "",
            createdAt = LocalDateTime.now(),
            updateAt =  LocalDateTime.now(),
            colorHex = ""
        )

        fun getStubs() = List(20) { index ->
            NONE.copy(id = index.toString(), title = "category $index")
        }
    }
}
