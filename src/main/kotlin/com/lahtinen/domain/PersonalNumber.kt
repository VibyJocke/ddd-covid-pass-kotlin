package com.lahtinen.domain

data class PersonalNumber(val value: String) {
    init {
        require(value matches "^(\\d{2})(\\d{2})(\\d{2})(\\d{2})([-])(\\d{4})$".toRegex())
    }
}
