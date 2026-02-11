package com.example.daysoftheyear.presentation

import com.example.daysoftheyear.domain.model.DateEntry

data class DateState(
    val currentYear: Int = -1,
    val currentDay: Int = -1,
    val lengthOfYear: Int = 365,
    val activeEntry: DateEntry? = null
)