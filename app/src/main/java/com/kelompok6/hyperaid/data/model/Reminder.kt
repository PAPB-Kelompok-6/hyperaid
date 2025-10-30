package com.kelompok6.hyperaid.data.model

data class Reminder(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val time: String,
    val days: String,
    val isActive: Boolean
)
