package com.example.daysoftheyear.domain.model

data class DateEntry (
    val year: Int,
    val day: Int,
    val textInput: String,
) {
    companion object{
        fun summarize(entry: DateEntry): String {
            return "${entry.year}-${entry.day}: ${entry.textInput}"
        }
    }
}