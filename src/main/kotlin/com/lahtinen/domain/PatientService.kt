package com.lahtinen.domain

import com.lahtinen.repository.AggregateRepository
import com.lahtinen.repository.InMemoryEventStore
import java.util.UUID

class PatientService {
    private val patientRepository = AggregateRepository<Patient>(InMemoryEventStore())

    fun registerPatient(command: RegisterPatient): UUID {
        return Patient.registerNew(command.nin).apply { patientRepository.save(this) }.aggregateId
    }
}