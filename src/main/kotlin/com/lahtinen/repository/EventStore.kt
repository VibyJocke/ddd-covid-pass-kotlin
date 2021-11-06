package com.lahtinen.repository

import com.lahtinen.domain.Event
import java.util.UUID

interface EventStore {
    fun save(aggregateId: UUID, events: List<Event>, version: Int)
    fun getByAggregateId(aggregateId: UUID): List<Event>
}