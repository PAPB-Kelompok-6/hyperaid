package com.kelompok6.hyperaid.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.kelompok6.hyperaid.data.enum.Gender

class ProfileViewModel : ViewModel() {
    val id: String = ""
    val email: String = ""
    val password: String = ""
    val age: Int = 0
    val gender: Gender = Gender.OTHERS

    val avatar: String = ""
    val name: String = ""
    val address: String = ""
    val languagePreference: String = "id_en"

    fun editAccount(id: String) {

    }

    fun deleteAccount(id: String) {

    }
}