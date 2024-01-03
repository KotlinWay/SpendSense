package info.javaway.spend_sense.events.model

import info.javaway.spend_sense.categories.model.Category

data class SpendEventUI(
    val id: String,
    val category: Category,
    val title: String,
    val cost: Double
)
