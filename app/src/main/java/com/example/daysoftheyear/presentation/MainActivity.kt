package com.example.daysoftheyear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.daysoftheyear.ui.theme.DaysOfTheYearTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity () : ComponentActivity() {


    val viewModel: DateViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val state = viewModel.state.collectAsStateWithLifecycle()


            DaysOfTheYearTheme {
                Scaffold(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                ) { innerPadding ->


                    DaysOfTheYearScreen(
                        modifier = Modifier.Companion.padding(innerPadding),
                        state,
                        onDismiss = { dateEntry ->
                            viewModel.dateSaved(dateEntry)
                        },
                        onClick = { year, day ->
                            viewModel.onDateClicked(year, day)
                        })
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTodayDate()
        //  view model function to check current date
        println("system resumed")
    }

}