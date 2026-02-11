package com.example.daysoftheyear.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.daysoftheyear.domain.model.DateEntry


@Composable
fun DaysOfTheYear(
    modifier: Modifier,
    isPassed: Boolean = false,
    isToday: Boolean,
    year: Int,
    day: Int,
    onClick: (Int,Int) -> Unit
) {
    var color by remember { mutableStateOf(0xFFFFFFFF) }

    Box(
        modifier = modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(
                if (isPassed) Color(color) else if (isToday) Color.Green else Color(
                    0xFF333333
                )
            )
            .fillMaxSize()
            .clickable(
                true,
                onClick = {
                    onClick(year, day)
                },
            )
    )
}
