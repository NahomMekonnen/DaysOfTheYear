package com.example.daysoftheyear.di

import com.example.daysoftheyear.data.local.EntryDatabase
import com.example.daysoftheyear.data.local.dao.EntryDao
import com.example.daysoftheyear.data.repository.DateRepository
import com.example.daysoftheyear.data.repository.DateRepositoryImpl
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
object FeaturesModule {

    @Provides
    @Singleton
    fun provideDateDao(db: EntryDatabase): EntryDao {
        return db.entryDao
    }

    @Provides
    @Singleton
    fun providesDateRepository(dao: EntryDao): DateRepository {
        return DateRepositoryImpl(dao)
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