package com.lahtinen.domain

import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class Patient private constructor(personalNumber: PersonalNumber) :
    AggregateRoot(AGGREGATE_TYPE_NAME, personalNumber.value) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val vaccinations = mutableListOf<Vaccination>()
    private var name = ""

    companion object {
        const val AGGREGATE_TYPE_NAME = "Patient"

        fun registerNew(personalNumber: PersonalNumber, name: String): Patient {
            return Patient(personalNumber).apply { registerNew(personalNumber, name) }
        }

        fun fromHistory(personalNumber: PersonalNumber, events: List<Event>): Patient {
            return Patient(personalNumber).apply { applyEvents(events) }
        }
    }

    private fun registerNew(personalNumber: PersonalNumber, name: String) {
        applyNew(PatientRegistered(personalNumber, LocalDateTime.now(), name))
    }

    fun reportVaccinated(personalNumber: PersonalNumber, injectionDate: LocalDateTime, vaccineType: String): Patient {
        applyNew(PatientVaccinated(personalNumber, injectionDate, vaccineType))
        return this
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
        name = event.name
    }

    private fun onVaccinated(event: PatientVaccinated) {
        vaccinations.add(Vaccination(event.date, event.vaccineType))
    }
}
