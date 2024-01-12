package info.javaway.spend_sense.categories.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryApi(
    @SerialName("idLocal")
    val id: String?,
    val title: String?,
    val description: String?,
    val colorHex: String?,
    @Contextual
    @SerialName("createdAtLocal")
    val createdAt: LocalDateTime?,
    @Contextual
    @SerialName("updatedAtLocal")
    val updatedAt: LocalDateTime?,
)









