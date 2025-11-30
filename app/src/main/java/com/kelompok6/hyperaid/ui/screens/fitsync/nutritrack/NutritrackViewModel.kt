package com.kelompok6.hyperaid.ui.screens.fitsync.nutritrack

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok6.hyperaid.data.model.NutrientInfo
import com.kelompok6.hyperaid.data.model.NutritionData
import com.kelompok6.hyperaid.data.repository.NutritionRepository
import com.kelompok6.hyperaid.utils.GeminiFoodPredictor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class NutritrackViewModel(
    private val repository: NutritionRepository = NutritionRepository()
) : ViewModel() {

    private val _nutritionData = MutableStateFlow<NutritionData?>(null)
    val nutritionData: StateFlow<NutritionData?> = _nutritionData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val allNutritionData = repository.getAllNutritionData()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    private val _todayTotals = MutableStateFlow<NutritionData?>(null)
    val todayTotals: StateFlow<NutritionData?> = _todayTotals.asStateFlow()

    fun loadTodayTotals() {
        viewModelScope.launch {
            repository.getTodayNutritionList().collect { meals ->
                if (meals.isNotEmpty()) {
                    val totalCarbo = meals.sumOf { it.carbohydrate }
                    val totalProtein = meals.sumOf { it.protein }
                    val totalFiber = meals.sumOf { it.fiber }
                    val totalFat = meals.sumOf { it.fat }
                    val totalGrams = totalCarbo + totalProtein + totalFiber + totalFat

                    _todayTotals.value = NutritionData(
                        id = "today_total",
                        date = LocalDate.now().toString(),
                        time = "Today",
                        totalGrams = totalGrams,
                        carbohydrate = totalCarbo,
                        protein = totalProtein,
                        fiber = totalFiber,
                        fat = totalFat,
                        name = "Today",
                        portion = 1
                    )

                    Log.d("NutritrackTotal", _todayTotals.value?.totalGrams.toString())
                } else {
                    _todayTotals.value = null
                }
            }
        }
    }

    fun saveNutritionData(data: NutritionData) {
        viewModelScope.launch {
            try {
                repository.saveNutritionData(data)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun predictAndSave(
        foodName: String,
        mealTime: String,
        portion: Int,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("NutritrackVM", "Calling GeminiFoodPredictor.predict")
                val prediction = GeminiFoodPredictor.predict(foodName, portion)
                Log.d("NutritrackVM", "Prediction result: $prediction")

                val total =
                    (prediction.carbo + prediction.protein + prediction.serat + prediction.lemak).toInt()

                val data = NutritionData(
                    id = "",
                    date = getTodayDate(),
                    time = mealTime,
                    totalGrams = total,
                    carbohydrate = prediction.carbo.toInt(),
                    protein = prediction.protein.toInt(),
                    fiber = prediction.serat.toInt(),
                    fat = prediction.lemak.toInt(),
                    name = foodName,
                    portion = portion
                )

                Log.d("NutritrackVM", "Saving nutrition data to Firestore: $data")
                repository.saveNutritionData(data)

                Log.d("NutritrackVM", "Saved successfully, calling onSuccess")
                onSuccess()
            } catch (e: Exception) {
                Log.e("NutritrackVM", "Error in predictAndSave", e)
                onError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getTodayDate(): String {
        return java.time.LocalDate.now().toString()
    }
}