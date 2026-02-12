package com.example.daysoftheyear.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.daysoftheyear.data.local.dao.EntryDao
import com.example.daysoftheyear.data.local.entity.EntryDbo

@Database(
    entities = [EntryDbo::class],
    version = 1,
    exportSchema = false
)
abstract class EntryDatabase : RoomDatabase(){
    abstract  val entryDao: EntryDao
    companion object {
        const val DATABASE_NAME = "entry_db"
    }
}