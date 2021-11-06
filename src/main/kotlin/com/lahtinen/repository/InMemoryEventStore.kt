package com.lahtinen.repository

import com.lahtinen.domain.Event
import java.util.UUID

// TODO: Implement simple atomicity/thread safety
class InMemoryEventStore : EventStore {
    private val eventsByAggregateId = mutableMapOf<UUID, List<Event>>()

    override fun save(aggregateId: UUID, events: List<Event>, version: Int) {
        if (!events.all { it.aggregateId == aggregateId }) {
            throw IllegalArgumentException("Cannot save events of from different aggregates")
        }
        if ((eventsByAggregateId[aggregateId]?.size ?: 0) != version) {
            throw IllegalStateException("Changes have occurred since aggregate was read")
        }

        eventsByAggregateId[aggregateId] = eventsByAggregateId.getOrDefault(aggregateId, listOf()).plus(events)
    }

    override fun getByAggregateId(aggregateId: UUID) = eventsByAggregateId.getOrDefault(aggregateId, listOf())
}