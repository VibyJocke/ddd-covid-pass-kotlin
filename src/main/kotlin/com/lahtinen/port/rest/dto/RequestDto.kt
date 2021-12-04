package com.lahtinen.port.rest.dto

import java.time.LocalDateTime

data class RegisterPatientRequest(val personal_number: String, val name: String)
data class VaccinatePatientRequest(
    val personal_number: String, val vaccine_type: String, val injection_date: LocalDateTime
)
