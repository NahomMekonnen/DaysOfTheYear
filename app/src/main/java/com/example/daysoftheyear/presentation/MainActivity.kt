package com.example.daysoftheyear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.daysoftheyear.ui.theme.Background
import com.example.daysoftheyear.ui.theme.DaysOfTheYearTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {


    val viewModel: DateViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backgroundColor = Background
        enableEdgeToEdge(

            statusBarStyle = SystemBarStyle.dark(
                scrim = backgroundColor.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = backgroundColor.toArgb()
            )
        )
        setContent {

            val state = viewModel.state.collectAsStateWithLifecycle()


            DaysOfTheYearTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->


                    DaysOfTheYearScreen(
                        modifier = Modifier.padding(innerPadding),
                        state,
                        onDismiss = { dateEntry ->
                            viewModel.dateSaved(dateEntry)
                        },
                        onClick = { year, day ->
                            viewModel.onDateClicked(year, day)
                        }
                    )
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