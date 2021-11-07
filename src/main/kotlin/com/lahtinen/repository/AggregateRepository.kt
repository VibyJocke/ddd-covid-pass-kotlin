package com.lahtinen.repository

import com.lahtinen.domain.AggregateRoot
import com.lahtinen.domain.Patient
import java.util.UUID

// TODO: make proper abstractions and applyEvents return the aggregate itself
class AggregateRepository<T : AggregateRoot<Patient>>(private val eventStore: EventStore) {
    fun save(aggregate: T) {
        eventStore.save(aggregate.aggregateId, aggregate.uncommittedEvents, aggregate.committedVersion)
    }

    fun getPatient(aggregateId: UUID): Patient? {
        return eventStore.getByAggregateId(aggregateId)
            .let { if (it.isEmpty()) null else Patient.fromHistory(aggregateId, it) }
    }
}
