package com.example.daysoftheyear.presentation

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.daysoftheyear.R
import com.example.daysoftheyear.domain.model.DateEntry
import com.example.daysoftheyear.presentation.components.DayOFTheYearBottomSheet
import com.example.daysoftheyear.presentation.components.DaysOfTheYear
import com.example.daysoftheyear.ui.theme.Background


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun DaysOfTheYearScreen(
    modifier: Modifier = Modifier,
    state: State<DateState>,
    onClick: (Int, Int) -> Unit,
    onDismiss: (DateEntry) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        val configuration = LocalConfiguration.current
        val windowSize = rememberWindowSize(configuration)
        val gridSpec = rememberLayoutSpec(windowSize)



        val currentDay = state.value.currentDay
        val currentYear = state.value.currentYear
        val lengthOfTheYear = state.value.lengthOfYear
        val remainingDays = lengthOfTheYear - currentDay
        var displayDay by remember { mutableIntStateOf(remainingDays) }

        val fontFamily = FontFamily(Font(R.font.cutive_mono_regular))

        var dragSelectedDayIndex by remember { mutableStateOf<Int?>(null) }
        val gridState = rememberLazyGridState()

        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Spacer(Modifier.height(5.dp))
        }

        Text(
            text = "${displayDay}",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = gridSpec.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
        Text(
            text = "days left",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = gridSpec.fontSize,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            println(gridSpec)
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(gridSpec.columns),
                verticalArrangement = Arrangement.spacedBy(gridSpec.spaceY),
                horizontalArrangement = Arrangement.spacedBy(gridSpec.spaceX),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = gridSpec.padding , end = gridSpec.padding, top = 10.dp)
                    .pointerInput(Unit) {
                        fun findClosestItem(offset: Offset): Int? {
                            val visibleItems = gridState.layoutInfo.visibleItemsInfo
                            if (visibleItems.isEmpty()) return null

                            return visibleItems.minByOrNull { item ->
                                val itemCenterX = item.offset.x + (item.size.width / 2)
                                val itemCenterY = item.offset.y + (item.size.height / 2)
                                val dx = offset.x - itemCenterX
                                val dy = offset.y - itemCenterY
                                (dx * dx + dy * dy)
                            }?.index
                        }

                        detectDragGesturesAfterLongPress(
                            onDragStart = { offset ->
                                findClosestItem(offset)?.let { dragSelectedDayIndex = it }
                            },
                            onDrag = { change, _ ->
                                findClosestItem(change.position)?.let { dragSelectedDayIndex = it }
                            },
                            onDragEnd = { dragSelectedDayIndex = null },
                            onDragCancel = { dragSelectedDayIndex = null }
                        )

                    },
            ) {
                items(lengthOfTheYear) { dayIndex ->
//                minus 1 because it's 0 indexed and currentDate starts from 1

                    val isPassed = if (dragSelectedDayIndex != null) {
                        dayIndex <= dragSelectedDayIndex!!
                    } else {
                        dayIndex < currentDay - 1
                    }

                    displayDay = if (dragSelectedDayIndex != null) {
                        lengthOfTheYear - (dragSelectedDayIndex!! + 1)
                    } else {
                        remainingDays
                    }

                    val isToday = if (dragSelectedDayIndex != null) {
                        isPassed
                    } else {
                        dayIndex == currentDay - 1
                    }
                    DaysOfTheYear(
                        modifier = Modifier,
                        isPassed = isPassed,
                        isToday = isToday,
                        year = currentYear,
                        day = dayIndex + 1,
                        onClick = onClick,
                        cellSize = gridSpec.cellSize
                    )
                }
            }

        }

    }
    val configuration = LocalConfiguration.current
    val gridSpec = rememberLayoutSpec(rememberWindowSize(configuration))
    if (state.value.activeEntry?.day != null) {
        DayOFTheYearBottomSheet(
            entry = state.value.activeEntry!!,
            onDismiss = onDismiss,
            configuration = configuration,
            beforeHeight =  gridSpec.beforeHeight,
            afterHeight = gridSpec.afterHeight

        )

    }


}

