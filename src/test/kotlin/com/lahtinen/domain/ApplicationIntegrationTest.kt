package com.lahtinen.domain

import com.google.gson.Gson
import com.lahtinen.module
import com.lahtinen.port.rest.dto.RegisterPatientRequest
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationIntegrationTest {

    private val mapper = Gson()

    @Test
    fun `registering a patient with valid personal_number and name returns 200`() {
        withTestApplication(Application::module) {
            registerPatient(RegisterPatientRequest("01234567-8900", "Joe"))
                .apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                }
        }
    }

    @Test
    fun `registering a patient with illegal personal_number returns 400`() {
        withTestApplication(Application::module) {
            registerPatient(RegisterPatientRequest("01234567", "Joe"))
                .apply {
                    assertEquals(HttpStatusCode.BadRequest, response.status())
                    assertEquals("Failed requirement.", response.content)
                }
        }
    }

    @Test
    fun `registering the same patient twice returns 400`() {
        withTestApplication(Application::module) {
            val request = RegisterPatientRequest("01234567-8900", "Joe")
            registerPatient(request)
            registerPatient(request)
                .apply {
                    assertEquals(HttpStatusCode.BadRequest, response.status())
                    assertEquals("Patient already exists", response.content)
                }
        }
    }

    private fun TestApplicationEngine.registerPatient(request: RegisterPatientRequest) =
        handleRequest(HttpMethod.Post, "/covid-pass/patient/register") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(mapper.toJson(request))
        }
}