package com.lahtinen.domain

import java.time.LocalDateTime

data class Vaccination(val date: LocalDateTime, val location: String, val doctorName: String)
