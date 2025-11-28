package com.kelompok6.hyperaid.ui.screens.fitsync.nutritrack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok6.hyperaid.data.model.NutritionData
import com.kelompok6.hyperaid.data.repository.NutritionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val repository: NutritionRepository
) : ViewModel() {

    private val _nutritionData = MutableStateFlow<NutritionData?>(null)
    val nutritionData: StateFlow<NutritionData?> = _nutritionData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadNutritionData(id: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (id != null) {
                    repository.getNutritionDataById(id).collect { data ->
                        _nutritionData.value = data
                    }
                } else {
                    repository.getTodayNutritionData().collect { data ->
                        _nutritionData.value = data
                    }
                }
            } catch (e: Exception) {
                // Handle error
                _nutritionData.value = null
            } finally {
                _isLoading.value = false
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
}