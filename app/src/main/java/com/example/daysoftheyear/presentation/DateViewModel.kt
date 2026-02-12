package com.example.daysoftheyear.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daysoftheyear.domain.model.DateEntry
import com.example.daysoftheyear.domain.usecase.JournalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class DateViewModel @Inject constructor(
    private val journalUseCases: JournalUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<DateState>(DateState())
    val state: StateFlow<DateState> = _state


    init {
        getTodayDate()

    }

    fun getTodayDate() = viewModelScope.launch {
        val date = LocalDate.now()
        val day = date.dayOfYear
        val lengthOfTheYear = date.lengthOfYear()

        _state.update {
            it.copy(
                lengthOfYear = lengthOfTheYear,
                currentDay = day,
                currentYear = date.year
            )
        }

        println(state.value)
    }


    fun onDateClicked(year: Int, day: Int) = viewModelScope.launch {
        println("on date click ${day}")
        // fetch date details
        // if the date exists in the data base
        // retrieve async

        if (day > state.value.currentDay) return@launch
        try {
            val result = journalUseCases.getDayEntryUseCase(year = year, day = day)

            _state.update { currentState ->
                currentState.copy(
                    activeEntry = result
                )

            }
        } catch (e: Exception) {
            println(e.message)
            _state.update {
                it.copy(
                    activeEntry = DateEntry(
                        year,
                        day,
                        ""
                    )
                )
            }
        }


    }


    fun dateSaved(dateEntry: DateEntry?) = viewModelScope.launch {
        // save to database
        // year ${state.value.currentyear},id:date, text:input
        // on the dbo it upsert
//
        println("on dismiss")
        println(DateEntry.summarize(dateEntry!!))
        println("#")

        withContext(Dispatchers.IO) {
            // save data
            journalUseCases.saveDayEntryUseCase(dateEntry)
        }

        _state.update {
            it.copy(
                activeEntry = null
            )
        }
    }

    /**
     *
     * data from the internet
     * data from the database can be fetched two ways
     * 1. we stall the ui -> // hold and wait ->  for database this in not allowed .. synchronous your ui waiting
     *
     * app accepts function // action is done and waits for result or response
     *
     */


//    suspend fun mockDataFromDatabase(day: Int): EntryDbo {
//        return EntryDbo(state.value.currentYear, day, "wublahubdub $day")
//            ?: throw Exception("data not found")
//    }


    // any logic
    // what date it is calculation
    // what year it is calculation
    // what text to show


}

