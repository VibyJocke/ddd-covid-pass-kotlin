package com.lahtinen.domain

abstract class AggregateRoot(val type: String, val aggregateId: String) {
    val uncommittedEvents = mutableListOf<Event>()
    var committedVersion = 0 // TODO: use something more robust instead of assuming version == numberOfEvents

    abstract fun apply(event: Event)

    fun applyNew(event: Event) {
        uncommittedEvents.add(event)
        apply(event)
    }

    fun applyEvents(events: List<Event>) {
        events.forEach(this::apply)
        committedVersion = events.size
    }
}
