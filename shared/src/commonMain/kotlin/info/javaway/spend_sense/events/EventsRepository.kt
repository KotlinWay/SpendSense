package info.javaway.spend_sense.events

import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.extensions.appLog
import kotlinx.coroutines.flow.flow

class EventsRepository {

    fun getAllFlow() = flow { emit(SpendEvent.getStubs()) }

    fun create(spendEvent: SpendEvent) = appLog("create event $spendEvent")
}