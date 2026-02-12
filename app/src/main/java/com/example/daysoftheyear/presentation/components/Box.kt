package com.example.daysoftheyear.presentation.components

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.daysoftheyear.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaysOfTheYear(
    modifier: Modifier,
    isPassed: Boolean = false,
    isToday: Boolean,
    year: Int,
    day: Int,
    onClick: (Int, Int) -> Unit
) {
    val passedColor = Color(0xFFFFFFFF)
    val remainingColor = Color(0xFF333333)
    val tooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()
    val date = LocalDate.ofYearDay(year, day)
    val displayDay = date.dayOfWeek.toString().lowercase()
    val displayMonth = date.monthValue

    val fontFamily = FontFamily(Font(R.font.cutive_mono_regular))



    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            PlainTooltip(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp)),
                contentColor = Color.White,
                containerColor = remainingColor
            ) {

                Text(
                    text = "${displayDay}, ${displayMonth}.${year.toString().takeLast(2)} ",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraLight,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                )
            }
        },
        state = tooltipState,
        modifier = modifier,
    ) {
        Box(
            modifier = modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(
                    if (isPassed) {
                        passedColor
                    } else if (isToday) {
                        passedColor
                    } else {
                        remainingColor
                    }
                )
                .fillMaxSize()
                .clickable(
                    true,
                    onClick = {
                        onClick(year, day)
                        scope.launch {
                            tooltipState.show(MutatePriority.Default)
                            delay(1500)
                            tooltipState.dismiss()
                        }
                    },
                )
        )
    }

}
