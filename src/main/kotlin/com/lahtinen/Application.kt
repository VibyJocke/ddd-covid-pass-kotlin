package com.lahtinen

import com.lahtinen.domain.PatientService
import com.lahtinen.domain.RegisterPatient
import com.lahtinen.port.rest.LocalDateTimeTypeAdapter
import com.lahtinen.port.rest.dto.RegisterPatientRequest
import com.lahtinen.port.rest.dto.RegisterPatientResponse
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.time.LocalDateTime


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
    install(ContentNegotiation) {
        gson { registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter()) }
    }
    install(Routing) {
        post("/covid-pass/patient/register") {
            call.receive<RegisterPatientRequest>()
                .let { patientService.registerPatient(RegisterPatient(it.patientNin)) }
                .let { call.respond(HttpStatusCode.OK, RegisterPatientResponse(it)) }
        }
    }
}
