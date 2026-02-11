package com.example.daysoftheyear.presentation.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.daysoftheyear.domain.model.DateEntry


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayOFTheYearBottomSheet(entry: DateEntry, onDismiss: (DateEntry) -> Unit) {
    val text = remember { mutableStateOf<String?>(entry.textInput) }

    ModalBottomSheet(
        modifier = Modifier,
        onDismissRequest = {
            onDismiss(
                entry.copy(
                    textInput = text.value ?: ""
                )
            )
        },
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false),
        containerColor = Color(0xFF171717),
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${entry.day} ${entry.year}"
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(18.dp),
                value = text.value ?: "",
                onValueChange =
                    {
                        text.value = it
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF232323),
                    unfocusedContainerColor = Color(0xFF232323),
                    focusedBorderColor = Color(0xFF232323),
                    unfocusedBorderColor = Color(0xFF232323)
                )
            )
        }

    }
}