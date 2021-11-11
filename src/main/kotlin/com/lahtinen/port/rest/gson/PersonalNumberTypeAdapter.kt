package com.lahtinen.port.rest.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.lahtinen.domain.PersonalNumber
import java.lang.reflect.Type

class PersonalNumberTypeAdapter : JsonSerializer<PersonalNumber>, JsonDeserializer<PersonalNumber> {
    override fun serialize(src: PersonalNumber, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): PersonalNumber {
        return PersonalNumber(json.asString)
    }
}