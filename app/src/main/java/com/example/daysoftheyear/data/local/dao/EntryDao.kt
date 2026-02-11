package com.example.daysoftheyear.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.daysoftheyear.data.local.entity.EntryDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM Entry")
    fun loadAllDates(): Flow<List<EntryDbo>>

    @Query("SELECT * FROM Entry where year = :year and day = :day")
    suspend fun getDate(year: Int, day: Int) : EntryDbo?

    @Upsert
    fun upsert(entryDbo: EntryDbo)

}