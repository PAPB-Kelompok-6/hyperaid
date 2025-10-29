package com.kelompok6.hyperaid.data.model

import com.kelompok6.hyperaid.data.enum.Gender

data class User (
    val id: String,
    val email: String,
    val password: String,
    val age: Int,
    val gender: Gender,

    val avatar: String = "",
    val name: String = "",
    val address: String = "",
    val languagePreference: String = "id_en",
)
