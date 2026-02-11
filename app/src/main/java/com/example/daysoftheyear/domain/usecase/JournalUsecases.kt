package com.example.daysoftheyear.domain.usecase

data class JournalUseCases(
    val getDayEntryUseCase: GetDayEntryUseCase,
    val getAllEntriesUseCase: GetAllEntriesUseCase,
    val saveDayEntryUseCase: SaveDayEntryUseCase
)
