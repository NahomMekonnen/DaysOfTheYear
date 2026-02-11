package com.example.daysoftheyear.domain.repository

import com.example.daysoftheyear.domain.model.DateEntry
import kotlinx.coroutines.flow.Flow

interface DateRepository {
    fun getJournals() : Flow<List<DateEntry>>
    suspend fun getJournal(year: Int, day: Int) : DateEntry?
    suspend fun editJournal(entry: DateEntry)
}