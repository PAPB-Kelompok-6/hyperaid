package com.kelompok6.hyperaid.ui.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok6.hyperaid.data.enum.Gender
import com.kelompok6.hyperaid.data.model.User
import com.kelompok6.hyperaid.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

sealed class SaveUserState {
    object Idle : SaveUserState()
    object Loading : SaveUserState()
    object Success : SaveUserState()
    data class Error(val message: String) : SaveUserState()
}

data class OnboardingState(
    val fullName: String = "",
    val languagePreference: String = "",
    val height: Double? = null,
    val weight: Double? = null,
    val gender: Gender? = null,
    val age: Int? = null,
    val avatar: String? = null,
    val address: String? = null,
    val isAlcoholic: Boolean? = null,
    val isSmoking: Boolean? = null
)

class OnboardingViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state = _state.asStateFlow()

    private val _saveState = MutableStateFlow<SaveUserState>(SaveUserState.Idle)
    val saveState = _saveState.asStateFlow()

    fun setLangDefaultValues() {
        _state.value = _state.value.copy(
            languagePreference = "en"
        )
    }

    fun setAboutDefaultValues() {
        _state.value = _state.value.copy(
            gender = Gender.MALE,
            height = 150.0,
            weight = 50.0,
            isSmoking = false,
            isAlcoholic = false
        )
    }

    fun update(block: (OnboardingState) -> OnboardingState) {
        _state.value = block(_state.value)
    }

    fun saveAll(userId: String) {
        val current = _state.value
        val user = User(
            id = userId,
            fullName = current.fullName,
            languagePreference = current.languagePreference.ifEmpty { "en" },
            height = current.height,
            weight = current.weight,
            gender = current.gender,
            age = current.age,
            avatar = current.avatar,
            address = current.address,
            isAlcoholic = current.isAlcoholic,
            isSmoking = current.isSmoking
        )

        _saveState.value = SaveUserState.Loading
        viewModelScope.launch {
            userRepository.saveUser(user) { result ->
                result.onSuccess {
                    _saveState.value = SaveUserState.Success
                }.onFailure { e ->
                    _saveState.value = SaveUserState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    suspend fun checkIfLanguageMissing(userId: String): Boolean {
        return suspendCancellableCoroutine { cont ->
            userRepository.getUser(userId) { result ->
                result.onSuccess { user ->
                    cont.resume(user.languagePreference.isNullOrEmpty()) { cause, _, _ -> }
                }.onFailure {
                    cont.resume(true) { cause, _, _ -> }
                }
            }
        }
    }

    suspend fun checkIfAboutIsMissing(userId: String): Boolean {
        return suspendCancellableCoroutine { cont ->
            userRepository.getUser(userId) { result ->
                result.onSuccess { user ->
                    cont.resume(user.gender == null) { cause, _, _ -> }
                }.onFailure {
                    cont.resume(true) { cause, _, _ -> }
                }
            }
        }
    }
}
