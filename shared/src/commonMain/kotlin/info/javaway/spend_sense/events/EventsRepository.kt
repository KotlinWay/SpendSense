package info.javaway.spend_sense.events

import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.events.model.SpendEventDao

class EventsRepository(
    private val dao: SpendEventDao
) {

    fun getAllFlow() = dao.getAllFlow()

    suspend fun getAll() = dao.getAll()

    suspend fun insertAll(events: List<SpendEvent>) = dao.insertAll(events)

    suspend fun create(spendEvent: SpendEvent) = dao.insert(spendEvent)
}