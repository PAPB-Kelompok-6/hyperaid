package com.kelompok6.hyperaid.data.model

data class NutritionData(
    val id: String = "",
    val date: String = "",
    val time: String = "",
    val totalGrams: Int = 0,
    val carbohydrate: Int = 0,
    val protein: Int = 0,
    val fiber: Int = 0,
    val fat: Int = 0,
    val name: String = "",
    val portion: Int = 0,
)

data class NutrientInfo(
    val target: Int,
)
