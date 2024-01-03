package info.javaway.spend_sense.platform

import java.util.UUID

actual fun randomUUID(): String = UUID.randomUUID().toString()