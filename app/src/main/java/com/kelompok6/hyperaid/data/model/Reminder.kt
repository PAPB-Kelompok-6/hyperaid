package com.kelompok6.hyperaid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_table")
data class Reminder(
    @PrimaryKey(true)
    val id: Int = 0,
    val title: String,
    val time: String,
    val days: List<String>,
    val isActive: Boolean = true
)
