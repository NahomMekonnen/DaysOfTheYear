package com.example.daysoftheyear.di

import android.app.Application
import androidx.room.Room
import com.example.daysoftheyear.data.local.EntryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesEntryDatabase(app: Application): EntryDatabase {
        return Room.databaseBuilder(
            app,
            EntryDatabase::class.java,
            EntryDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration(false).build()
    }


    // split here

}