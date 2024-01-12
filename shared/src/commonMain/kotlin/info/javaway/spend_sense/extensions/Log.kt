package info.javaway.spend_sense.extensions

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun appLog(message: String) {
    Napier.d("SpendSense: 🚀 $message")
}

fun initLogs() = Napier.base(DebugAntilog())