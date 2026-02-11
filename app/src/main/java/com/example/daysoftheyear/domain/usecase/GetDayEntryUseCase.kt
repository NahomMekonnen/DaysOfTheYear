package com.example.daysoftheyear.domain.usecase

import com.example.daysoftheyear.domain.model.DateEntry
import com.example.daysoftheyear.domain.repository.DateRepository

class GetDayEntryUseCase (
    private val repository: DateRepository
) {
    suspend operator fun invoke(year: Int, day: Int) : DateEntry? {
        return repository.getJournal(year = year, day = day)
    }
}