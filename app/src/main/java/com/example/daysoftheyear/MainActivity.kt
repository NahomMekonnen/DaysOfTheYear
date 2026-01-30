package com.example.daysoftheyear

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.daysoftheyear.ui.theme.DaysOfTheYearTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DaysOfTheYearTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                ) { innerPadding ->
                    DaysOfTheYearScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysOfTheYearScreen(modifier : Modifier = Modifier) {
    var date by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                date = LocalDate.now() 
            }
        }

        val filter = IntentFilter(Intent.ACTION_DATE_CHANGED)
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }


    val totalDaysOfTheYear = date.lengthOfYear()
    val today = date.dayOfYear
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = today.toString(),
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 200.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize().padding(top= 80.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(13),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(25.dp),
        ) {
            items(totalDaysOfTheYear) { day ->
                val isPassed = (day < today)
                DaysOfTheYear(isPassed)
            }
        }
    }
}

@Composable
fun DaysOfTheYear(isPassed: Boolean = false) {
    var color by mutableStateOf(0xFFFFFFFF)
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(if (isPassed) Color(color) else Color(0xFF333333))
            .fillMaxSize()
            .clickable(
                true,
                onClick = {

                },
            )
    )
}

@Preview
@Composable
fun DaysOfTheYearPreview() {
    DaysOfTheYearTheme(darkTheme = true) {
        DaysOfTheYearScreen()
    }
}