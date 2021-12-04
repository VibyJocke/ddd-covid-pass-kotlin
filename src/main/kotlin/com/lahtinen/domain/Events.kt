package com.lahtinen.domain

import java.time.LocalDateTime

abstract class Event(val aggregateId: String, val date: LocalDateTime)

class PatientRegistered(personalNumber: PersonalNumber, date: LocalDateTime, val name: String) :
    Event(personalNumber.value, date)

class PatientVaccinated(personalNumber: PersonalNumber, date: LocalDateTime, val vaccineType: String) :
    Event(personalNumber.value, date)
