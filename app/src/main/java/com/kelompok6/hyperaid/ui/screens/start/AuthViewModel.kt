package com.kelompok6.hyperaid.ui.screens.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kelompok6.hyperaid.data.model.User
import com.kelompok6.hyperaid.data.repository.UserRepository

class AuthViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
    var registerState by mutableStateOf<RegisterState>(RegisterState.Idle)
    var oAuthState by mutableStateOf<OAuthState>(OAuthState.Idle)

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            loginState = LoginState.Error("All fields are required")
            return
        }

        loginState = LoginState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginState = LoginState.Success(auth.currentUser)
                } else {
                    loginState = LoginState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }

    fun register(fullName: String, email: String, password: String) {
        if (email.isBlank() || password.isBlank() || fullName.isBlank()) {
            registerState = RegisterState.Error("All fields are required")
            return
        }

        registerState = RegisterState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(
                        id = auth.currentUser!!.uid,
                        fullName = fullName,
                        gender = null,
                        age = null,
                        avatar = null,
                        address = null
                    )

                    userRepository.saveUser(user) { result ->
                        result.onSuccess { registerState = RegisterState.Success(auth.currentUser) }
                        result.onFailure { e ->
                            registerState =
                                RegisterState.Error(e.message ?: "Failed to save profile")
                        }
                    }
                } else {
                    registerState = RegisterState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }

    // this need to be handle in screen
    // preferably using snackbar
    fun showAuthError(message: String) {
        registerState = RegisterState.Error(message)
        loginState = LoginState.Error(message)
    }

    fun loginWithGoogle(credential: AuthCredential) {
        oAuthState = OAuthState.Loading

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                oAuthState =
                    if (task.isSuccessful) OAuthState.Success(auth.currentUser) else OAuthState.Error(
                        task.exception?.message ?: "Unknown error"
                    )
            }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: FirebaseUser?) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val user: FirebaseUser?) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

sealed class OAuthState {
    object Idle : OAuthState()
    object Loading : OAuthState()
    data class Success(val user: FirebaseUser?) : OAuthState()
    data class Error(val message: String) : OAuthState()
}
