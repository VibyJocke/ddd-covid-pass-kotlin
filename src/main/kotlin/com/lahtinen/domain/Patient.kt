package com.lahtinen.domain

import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.UUID

class Patient private constructor(private val id: UUID) : AggregateRoot(AGGREGATE_TYPE_NAME, id) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val vaccinations = mutableListOf<Vaccination>()
    private var nin = ""

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
        applyNew(PatientRegistered(id, LocalDateTime.now(), nin))
    }

    // Event handlers below

    override fun apply(event: Event) {
        when (event) {
            is PatientRegistered -> onRegistered(event)
            is PatientVaccinated -> onVaccinated(event)
            else -> log.warn("Event of type '${event::class.simpleName}' not supported.")
        }
    }

    private fun onRegistered(event: PatientRegistered) {
        nin = event.nin
    }

    private fun onVaccinated(event: PatientVaccinated) {
        vaccinations.add(Vaccination(event.date, event.location, event.doctorName))
    }
}
