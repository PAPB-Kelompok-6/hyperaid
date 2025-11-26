package com.kelompok6.hyperaid.ui.screens.fitsync.bmi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.kelompok6.hyperaid.data.model.BMI
import com.kelompok6.hyperaid.data.repository.BMIRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BMIViewModel(private val bmiRepository: BMIRepository) : ViewModel() {
    private val _bmi = MutableStateFlow<List<BMI>>(emptyList())
    val bmi: StateFlow<List<BMI>> = _bmi

    fun fetchBMI () {
        viewModelScope.launch {
            bmiRepository.getBMI().collect { bmiList -> _bmi.value = bmiList
            }
        }
    }

    fun addBMI (bmi: BMI) {
        viewModelScope.launch {
            bmiRepository.saveBMI(bmi)
        }
    }
}

class BMIViewModelFactory(private val repository: BMIRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BMIViewModel::class.java)) {
            return BMIViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}