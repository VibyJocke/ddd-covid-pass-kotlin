package com.lahtinen.domain

import java.time.LocalDateTime
import java.util.UUID

data class RegisterPatient(val nin: String)
data class ReportPatientVaccinated(val patientId: UUID, val date: LocalDateTime, val location: String, val doctorName: String)
data class IssueVaccinationPassport(val patientId: UUID, val issuer: String)
data class MarkPatientEligibleForVaccinationPassport(val patientId: UUID)
data class RemovePatientEligibilityForVaccinationPassport(val patientId: UUID)