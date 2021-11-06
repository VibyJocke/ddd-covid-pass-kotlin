package com.lahtinen.domain

import java.time.LocalDateTime
import java.util.UUID

abstract class Event(val aggregateId: UUID, val date: LocalDateTime)
class PatientRegistered(patientId: UUID, patientNin: String, date: LocalDateTime) : Event(patientId, date)
class PatientVaccinated(patientId: UUID, date: LocalDateTime, val location: String, val doctorName: String) : Event(patientId, date)
class PatientEligible(patientId: UUID, date: LocalDateTime) : Event(patientId, date)
class PatientEligibilityExpired(patientId: UUID, date: LocalDateTime) : Event(patientId, date)
class VaccinationPassportIssued(patientId: UUID, date: LocalDateTime) : Event(patientId, date)
class VaccinationPassportRevoked(patientId: UUID, date: LocalDateTime) : Event(patientId, date)