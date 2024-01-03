package info.javaway.spend_sense.platform

import platform.Foundation.NSUUID

actual fun randomUUID() = NSUUID().UUIDString()