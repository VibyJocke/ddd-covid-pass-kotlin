package com.lahtinen

import com.lahtinen.domain.PatientService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module(patientService: PatientService = PatientService()) {
    install(DefaultHeaders)
    install(CallLogging)
    configureExceptionMapper()
    configureJson()
    configureRouting(patientService)
}
