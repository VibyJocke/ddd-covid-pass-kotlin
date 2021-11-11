package com.lahtinen

import com.lahtinen.domain.PatientService
import com.lahtinen.domain.RegisterPatient
import com.lahtinen.port.rest.dto.RegisterPatientRequest
import com.lahtinen.port.rest.dto.RegisterPatientResponse
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post

fun Application.configureRouting(patientService: PatientService) {
    install(Routing) {
        post("/covid-pass/patient/register") {
            call.receive<RegisterPatientRequest>()
                .let { patientService.registerPatient(RegisterPatient(it.patientNin.value)) }
                .let { call.respond(HttpStatusCode.OK, RegisterPatientResponse(it)) }
        }
    }
}