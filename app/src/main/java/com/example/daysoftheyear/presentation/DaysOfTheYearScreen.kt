package com.example.daysoftheyear.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.daysoftheyear.R
import com.example.daysoftheyear.domain.model.DateEntry
import com.example.daysoftheyear.presentation.components.DayOFTheYearBottomSheet
import com.example.daysoftheyear.presentation.components.DaysOfTheYear


@Composable
fun DaysOfTheYearScreen(
    modifier: Modifier = Modifier,
    state: State<DateState>,
    onClick: (Int, Int) -> Unit,
    onDismiss: (DateEntry) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val configuration = LocalConfiguration.current
        val cells = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 35 else 14
        val padding =
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 45.dp else 25.dp
        val fontSize =
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 20.sp else 20.sp
        val itemSize =
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 10.dp else 12.dp

        val currentDay = state.value.currentDay
        val currentYear = state.value.currentYear
        val lengthOfTheYear = state.value.lengthOfYear
        val remainingDays = lengthOfTheYear - currentDay

        val fontFamily = FontFamily(Font(R.font.cutive_mono_regular))

        Text(
            text = "${remainingDays}",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "days left",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center
        )

//        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Spacer()
//        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(cells),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(start = padding, end = padding, top = 15.dp),
        ) {
            items(lengthOfTheYear) { day ->
//                minus 1 because it's 0 indexed and currentDate starts from 1
                val year = currentYear
                val today = currentDay
                val isPassed = (day < currentDay - 1)
                val isToday = (day == today - 1)
                DaysOfTheYear(
                    modifier = Modifier.size(itemSize),
                    isPassed = isPassed,
                    isToday = isToday,
                    year = year,
                    day = day + 1,
                    onClick = onClick
                )
            }
        }
    }

    if (state.value.activeEntry?.day != null) {
        DayOFTheYearBottomSheet(
            entry = state.value.activeEntry!!,
            onDismiss = onDismiss,
        )

    }
}