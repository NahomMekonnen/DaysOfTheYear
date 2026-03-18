package com.example.daysoftheyear.domain.usecase

import com.example.daysoftheyear.domain.model.DateEntry
import com.example.daysoftheyear.data.repository.DateRepository
import kotlinx.coroutines.flow.Flow

class GetAllEntriesUseCase (
    private val repository: DateRepository
) {
    operator fun invoke () : Flow<List<DateEntry>> {
        return repository.getJournals()
    }
}
