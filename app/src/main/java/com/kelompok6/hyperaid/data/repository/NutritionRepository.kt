package com.kelompok6.hyperaid.data.repository

import com.kelompok6.hyperaid.data.model.NutritionData
import kotlinx.coroutines.flow.Flow

interface NutritionRepository {
    suspend fun getNutritionDataById(id: String): Flow<NutritionData?>
    suspend fun getTodayNutritionData(): Flow<NutritionData?>
    suspend fun saveNutritionData(data: NutritionData)
}