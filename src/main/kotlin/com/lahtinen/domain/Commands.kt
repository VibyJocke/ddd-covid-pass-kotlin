package com.lahtinen.domain

import java.time.LocalDateTime

data class RegisterPatient(val personalNumber: PersonalNumber, val name: String)
data class ReportPatientVaccinated(
    val personalNumber: PersonalNumber,
    val injectionDate: LocalDateTime,
    val vaccineType: String
)
