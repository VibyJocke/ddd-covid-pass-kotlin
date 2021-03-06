package com.lahtinen.repository

import com.lahtinen.domain.Event

// TODO: Implement simple atomicity/thread safety
class InMemoryEventStore : EventStore {
    private val eventsByAggregateId = mutableMapOf<CompositeKey, List<Event>>()

    override fun getEventsById(type: String, id: String) =
        eventsByAggregateId.getOrDefault(CompositeKey(type, id), listOf())

    override fun saveEvents(type: String, id: String, events: List<Event>, version: Int) {
        val key = CompositeKey(type, id)
        validateAllEventsAreOfTheSameAggregate(events, id)
        validatePersistedVersionMatchesLastReadVersion(key, version)
        eventsByAggregateId[key] = eventsByAggregateId.getOrDefault(key, listOf()).plus(events)
    }

    private fun validatePersistedVersionMatchesLastReadVersion(key: CompositeKey, version: Int) {
        if ((eventsByAggregateId[key]?.size ?: 0) != version) {
            throw IllegalStateException("Changes have occurred since aggregate was last read")
        }
    }

    private fun validateAllEventsAreOfTheSameAggregate(events: List<Event>, id: String) {
        if (events.allSameAggregateAs(id)) {
            throw IllegalArgumentException("Cannot save events of from different aggregates")
        }
    }

    private fun List<Event>.allSameAggregateAs(id: String) = !this.all { it.aggregateId == id }

    data class CompositeKey(val type: String, val id: String)
}
