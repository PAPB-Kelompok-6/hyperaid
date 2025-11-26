package com.kelompok6.hyperaid.data.model

import com.kelompok6.hyperaid.data.enum.Gender

data class User(
    val id: String = "",
    val fullName: String = "",

    val gender: Gender? = null,
    val age: Int? = null,
    val avatar: String? = null,
    val address: String? = null,
    val height: Double? = null,
    val weight: Double? = null,
    val isAlcoholic: Boolean? = null,
    val isSmoking: Boolean? = null,

    val languagePreference: String = "",
)
