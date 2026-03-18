package com.example.daysoftheyear.data.repository

import com.example.daysoftheyear.data.local.dao.EntryDao
import com.example.daysoftheyear.data.local.entity.EntryDbo
import com.example.daysoftheyear.domain.model.DateEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DateRepositoryImpl(
    private val dao: EntryDao
) : DateRepository {
    override fun getJournals(): Flow<List<DateEntry>> {
        return dao.loadAllDates().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getJournal(
        year: Int,
        day: Int
    ): DateEntry {
        return dao.getDate(year = year, day = day)?.toDomain()
            ?: throw Exception("get journal by id not working")
    }

    override suspend fun editJournal(entry: DateEntry) {
        dao.upsert(entry.toData())
    }

}

fun EntryDbo.toDomain(): DateEntry =
    DateEntry(
        year = year,
        day = day,
        textInput = textInput
    )

fun DateEntry.toData(): EntryDbo =
    EntryDbo(
        year = year,
        day = day,
        textInput = textInput
    )