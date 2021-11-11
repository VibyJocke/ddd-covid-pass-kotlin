package com.lahtinen

import com.lahtinen.domain.PersonalNumber
import com.lahtinen.port.rest.gson.LocalDateTimeTypeAdapter
import com.lahtinen.port.rest.gson.PersonalNumberTypeAdapter
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import java.time.LocalDateTime

fun Application.configureJson() {
    install(ContentNegotiation) {
        gson {
            registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
            registerTypeAdapter(PersonalNumber::class.java, PersonalNumberTypeAdapter())
        }
    }
}