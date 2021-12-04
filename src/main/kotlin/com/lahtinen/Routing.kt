package com.lahtinen

import com.lahtinen.domain.PatientService
import com.lahtinen.domain.PersonalNumber
import com.lahtinen.domain.RegisterPatient
import com.lahtinen.domain.ReportPatientVaccinated
import com.lahtinen.port.rest.dto.RegisterPatientRequest
import com.lahtinen.port.rest.dto.VaccinatePatientRequest
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
                .let { patientService.registerPatient(RegisterPatient(PersonalNumber(it.personal_number), it.name)) }
                .let { call.respond(HttpStatusCode.OK) }
        }
        post("/covid-pass/patient/vaccinate") {
            call.receive<VaccinatePatientRequest>()
                .let {
                    patientService.reportPatientVaccinated(
                        ReportPatientVaccinated(
                            PersonalNumber(it.personal_number),
                            it.injection_date,
                            it.vaccine_type
                        )
                    )
                }
                .let { call.respond(HttpStatusCode.OK) }
        }
    }
}