package com.lahtinen

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import java.lang.IllegalArgumentException

fun Application.configureExceptionMapper() {
    install(StatusPages) {
        exception<IllegalArgumentException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "")
        }
    }
}