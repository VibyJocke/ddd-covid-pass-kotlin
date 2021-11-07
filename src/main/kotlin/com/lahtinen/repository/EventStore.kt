package com.lahtinen.repository

import com.lahtinen.domain.Event
import java.util.UUID

interface EventStore {
    fun saveEvents(type: String, id: UUID, events: List<Event>, version: Int)
    fun getEventsById(type: String, id: UUID): List<Event>
}
