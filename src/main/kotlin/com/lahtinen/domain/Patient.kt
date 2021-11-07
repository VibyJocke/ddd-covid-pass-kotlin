package com.lahtinen.domain

import java.time.LocalDateTime
import java.util.UUID

class Patient private constructor(private val id: UUID) : AggregateRoot(AGGREGATE_TYPE_NAME, id) {
    private val vaccinations = mutableListOf<Vaccination>()

    companion object {
        const val AGGREGATE_TYPE_NAME = "Patient"

        fun registerNew(nin: String): Patient {
            return Patient(UUID.randomUUID()).apply { registerNew(nin) }
        }

        fun fromHistory(patientId: UUID, events: List<Event>): Patient {
            return Patient(patientId).apply { applyEvents(events) }
        }
    }

    private fun registerNew(nin: String) {
        applyNew(PatientRegistered(id, nin, LocalDateTime.now()))
    }

    // Event handlers below

    override fun apply(event: Event) {
        when (event) {
            is PatientVaccinated -> onVaccinated(event)
        }
    }

    private fun onVaccinated(event: PatientVaccinated) {
        vaccinations.add(Vaccination(event.date, event.location, event.doctorName))
    }
}
