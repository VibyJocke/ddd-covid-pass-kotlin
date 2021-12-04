package com.lahtinen.repository

import com.lahtinen.domain.Event

interface EventStore {
    fun saveEvents(type: String, id: String, events: List<Event>, version: Int)
    fun getEventsById(type: String, id: String): List<Event>
}
