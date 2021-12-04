package com.lahtinen.repository

import com.lahtinen.domain.AggregateRoot
import com.lahtinen.domain.Patient
import com.lahtinen.domain.PersonalNumber

class AggregateRepository<T : AggregateRoot>(private val eventStore: EventStore) {
    fun save(aggregate: T) {
        eventStore.saveEvents(
            aggregate.type,
            aggregate.aggregateId,
            aggregate.uncommittedEvents,
            aggregate.committedVersion
        )
    }

    fun getPatient(personalNumber: PersonalNumber): Patient? {
        return eventStore.getEventsById(Patient.AGGREGATE_TYPE_NAME, personalNumber.value)
            .let { if (it.isEmpty()) null else Patient.fromHistory(personalNumber, it) }
    }
}
