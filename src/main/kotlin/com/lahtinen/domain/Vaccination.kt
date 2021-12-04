package com.lahtinen.domain

import java.time.LocalDateTime

enum class ApprovedVaccines(val manufacturer: String, val type: String) {
    MODERNA("Moderna", "Spikevax"),
    PFIZER_BIONTECH("Pfizer/BioNTech", "Comirnaty"),
    JANSSEN("Janssen", "Ad26.COV2.S"),
    OXFORD_ASTRAZENECA("Oxford/AstraZeneca", "Vaxzevria"),
}

data class Vaccination(val date: LocalDateTime, val vaccineType: String) {
    init {
        ApprovedVaccines.values().find { it.type == vaccineType }
            ?: throw IllegalArgumentException("Non-approved vaccine")
    }
}
