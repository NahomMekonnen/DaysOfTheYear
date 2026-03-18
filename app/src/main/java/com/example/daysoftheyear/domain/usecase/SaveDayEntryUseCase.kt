package com.example.daysoftheyear.domain.usecase

import com.example.daysoftheyear.domain.model.DateEntry
import com.example.daysoftheyear.data.repository.DateRepository

class SaveDayEntryUseCase (
    private val repository: DateRepository
) {
    suspend operator fun invoke(dateEntry: DateEntry) {
        repository.editJournal(dateEntry)
    }
}