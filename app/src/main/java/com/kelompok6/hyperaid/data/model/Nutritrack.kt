package com.kelompok6.hyperaid.data.model

data class NutritionData(
    val date: String,
    val time: String,
    val totalGrams: Int,
    val carbohydrate: NutrientInfo,
    val protein: NutrientInfo,
    val fiber: NutrientInfo,
    val fat: NutrientInfo,
    val meals: List<MealDetail>
)

data class NutrientInfo(
    val current: Int,
    val target: Int,
    val percentage: Float
)

data class MealDetail(
    val name: String,
    val portion: Int,
    val carbohydrate: Int,
    val fat: Int,
    val fiber: Int,
    val protein: Int
)