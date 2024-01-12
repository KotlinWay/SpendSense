package info.javaway.spend_sense.network

import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "DateTimeSerializer", PrimitiveKind.LONG
    )

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return kotlin.runCatching {
            decoder.decodeLong().let { Instant.fromEpochMilliseconds(it) }
                .toLocalDateTime(TimeZone.currentSystemDefault())
        }.getOrNull() ?: LocalDateTime.now()
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val longValue = value.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        encoder.encodeLong(longValue)
    }

}

object DateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "DateSerializer", PrimitiveKind.LONG
    )

    override fun deserialize(decoder: Decoder): LocalDate {
        return kotlin.runCatching {
            decoder.decodeLong().let { Instant.fromEpochMilliseconds(it) }.toLocalDateTime(
                TimeZone.currentSystemDefault()
            ).date
        }.getOrNull() ?: LocalDateTime.now().date
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val longValue = value
            .atTime(LocalDateTime.now().time)
            .toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
        encoder.encodeLong(longValue)
    }

}
