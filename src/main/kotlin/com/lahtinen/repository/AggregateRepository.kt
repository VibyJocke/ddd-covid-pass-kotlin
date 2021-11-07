package com.lahtinen.repository

import com.lahtinen.domain.AggregateRoot
import com.lahtinen.domain.Patient
import java.util.UUID

class AggregateRepository<T : AggregateRoot>(private val eventStore: EventStore) {
    fun save(aggregate: T) {
        eventStore.saveEvents(aggregate.type, aggregate.aggregateId, aggregate.uncommittedEvents, aggregate.committedVersion)
    }

    fun getPatient(aggregateId: UUID): Patient? {
        return eventStore.getEventsById(Patient.AGGREGATE_TYPE_NAME, aggregateId)
            .let { if (it.isEmpty()) null else Patient.fromHistory(aggregateId, it) }
    }
}
