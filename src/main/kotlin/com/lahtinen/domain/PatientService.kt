package com.lahtinen.domain

import com.lahtinen.repository.AggregateRepository
import com.lahtinen.repository.InMemoryEventStore

class PatientService {
    private val patientRepository = AggregateRepository<Patient>(InMemoryEventStore())

    fun registerPatient(command: RegisterPatient) {
        try {
            Patient.registerNew(command.personalNumber, command.name)
                .apply { patientRepository.save(this) }
        } catch (e: IllegalStateException) {
            throw IllegalArgumentException("Patient already exists")
        }
    }

    fun reportPatientVaccinated(command: ReportPatientVaccinated) {
        patientRepository.getPatient(command.personalNumber)
            ?.reportVaccinated(command.personalNumber, command.injectionDate, command.vaccineType)
            ?.apply { patientRepository.save(this) } ?: throw IllegalArgumentException()
    }
}
