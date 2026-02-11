package com.example.daysoftheyear.di

import android.app.Application
import androidx.room.Room
import com.example.daysoftheyear.data.local.EntryDatabase
import com.example.daysoftheyear.data.repository.DateRepositoryImpl
import com.example.daysoftheyear.domain.repository.DateRepository
import com.example.daysoftheyear.domain.usecase.GetAllEntriesUseCase
import com.example.daysoftheyear.domain.usecase.GetDayEntryUseCase
import com.example.daysoftheyear.domain.usecase.JournalUseCases
import com.example.daysoftheyear.domain.usecase.SaveDayEntryUseCase
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
    fun providesEntryDatabase(app: Application) : EntryDatabase {
        return Room.databaseBuilder(
            app,
            EntryDatabase::class.java,
            EntryDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesDateRepository(db: EntryDatabase) : DateRepository {
        return DateRepositoryImpl(db.entryDao)
    }

    @Provides
    @Singleton
    fun provideJournalUseCases(repository: DateRepository): JournalUseCases {
        return JournalUseCases(
            getDayEntryUseCase = GetDayEntryUseCase(repository),
            getAllEntriesUseCase = GetAllEntriesUseCase(repository),
            saveDayEntryUseCase = SaveDayEntryUseCase(repository)
        )
    }
}