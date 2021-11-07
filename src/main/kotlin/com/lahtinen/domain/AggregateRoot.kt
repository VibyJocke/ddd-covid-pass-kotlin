package com.lahtinen.domain

import java.util.UUID

abstract class AggregateRoot<T>(val aggregateId: UUID) {
    val uncommittedEvents = mutableListOf<Event>()
    var committedVersion = 0 // TODO: use something more robust instead of assuming version == numberOfEvents

    abstract fun apply(event: Event)

    fun applyNew(event: Event) {
        uncommittedEvents.add(event)
        apply(event)
    }

    fun applyEvents(events: List<Event>): T {
        events.forEach(this::apply)
        committedVersion = events.size
        return this as T
    }
}
