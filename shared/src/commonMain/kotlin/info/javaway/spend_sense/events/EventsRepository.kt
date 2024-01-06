package info.javaway.spend_sense.events

import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.events.model.SpendEventDao
import info.javaway.spend_sense.extensions.appLog
import kotlinx.coroutines.flow.flow

class EventsRepository(
    private val dao: SpendEventDao
) {

    fun getAllFlow() = dao.getAllFlow()

    suspend fun create(spendEvent: SpendEvent) = dao.insert(spendEvent)
}