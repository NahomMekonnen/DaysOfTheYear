package com.example.daysoftheyear.domain.model

data class DateEntry(
    val year: Int,
    val day: Int,
    val textInput: String,
) {
    fun summarize(): String {
        return "${this.year}-${this.day}: ${this.textInput}"
    }

}