package com.example.daysoftheyear.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.daysoftheyear.R
import com.example.daysoftheyear.domain.model.DateEntry
import com.example.daysoftheyear.ui.theme.Background
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayOFTheYearBottomSheet(
    entry: DateEntry,
    onDismiss: (DateEntry) -> Unit,
    configuration: Configuration,
    beforeHeight: Dp,
    afterHeight: Dp
) {
    var text by rememberSaveable { mutableStateOf<String?>(entry.textInput) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    var isFocused by remember { mutableStateOf(false) }

    val textFieldColor = Color(0xFF202020)
    val bottomSheetColor = Background
    val date = LocalDate.ofYearDay(entry.year, entry.day)
    val displayMonth = date.month.toString().lowercase()
    val displayDay = date.dayOfWeek.toString().lowercase()
    val year = entry.year

    val fontFamily = FontFamily(Font(R.font.cutive_mono_regular))

    ModalBottomSheet(
        modifier = Modifier,
        onDismissRequest = {
            onDismiss(
                entry.copy(
                    textInput = text ?: ""
                )
            )
        },
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false),
        containerColor = bottomSheetColor,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${displayDay}, ${displayMonth} ${year}",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                color = Color.White.copy(alpha = 0.5f),
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraLight,
                fontSize = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 16.sp else 12.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height( if (isFocused) afterHeight else beforeHeight)
                    .padding(16.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }.animateContentSize(
                        animationSpec = tween(
//                            durationMillis = 300,
//                            delayMillis = 400
                            durationMillis = 250, // Short duration feels snappy
                            easing = LinearOutSlowInEasing
                        )
                    ),
                value = text ?: "",
                onValueChange =
                    {
                        text = it
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = textFieldColor,
                    unfocusedContainerColor = textFieldColor,
                    focusedBorderColor = textFieldColor,
                    unfocusedBorderColor = textFieldColor
                ),
                textStyle = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            )
        }

    }
}