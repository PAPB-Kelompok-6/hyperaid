package com.kelompok6.hyperaid.data.model

import com.kelompok6.hyperaid.data.enum.Gender

data class User (
    val id: String,
    val fullName: String,

    val gender: Gender?,
    val age: Int?,
    val avatar: String?,
    val address: String?,
    val languagePreference: String = "id_en",
)
