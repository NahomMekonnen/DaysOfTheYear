package com.example.daysoftheyear.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "Entry",
    primaryKeys = ["year", "day"]
)

data class EntryDbo (
    val year:Int, //2026
    val day:Int, //45
    val textInput:String // Today i felt like deez
)