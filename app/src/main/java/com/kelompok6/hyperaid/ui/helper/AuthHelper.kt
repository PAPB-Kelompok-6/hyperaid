package com.kelompok6.hyperaid.ui.helper

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object AuthHelper {
    private val auth = FirebaseAuth.getInstance()

    // false positive, shall be cached globally
    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    suspend fun getDisplayName(): String {
        val user = auth.currentUser ?: return "Not Logged In"

        user.displayName?.let { return it }

        return try {
            val doc = db.collection("users").document(user.uid).get().await()
            doc.getString("fullName") ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }
}
