package info.javaway.spend_sense.categories.create

import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.platform.randomUUID
import kotlinx.datetime.LocalDateTime

data class CreateCategoryData(
    val title: String,
    val subtitle: String,
    val colorHex: String
)

fun CreateCategoryData.toCategory(dateTime: LocalDateTime) = Category(
    id = randomUUID(),
    title = title,
    description = subtitle,
    colorHex = colorHex,
    createdAt = dateTime,
    updatedAt = dateTime
)