package com.lahtinen.domain

import org.junit.Test

class PersonalNumberTest {

    @Test
    fun `Allows creation with basic yyyymmdd-xxxx pattern`() {
        PersonalNumber("18101211-1111")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Throws validation exception for pn with space`() {
        PersonalNumber("18101211 1111")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Throws validation exception for collapsed pn`() {
        PersonalNumber("181012111111")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Throws validation exception for 10-digit pn`() {
        PersonalNumber("101211-1111")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Throws validation exception for completely unsupported pattern`() {
        PersonalNumber("Hello world!")
    }
}
