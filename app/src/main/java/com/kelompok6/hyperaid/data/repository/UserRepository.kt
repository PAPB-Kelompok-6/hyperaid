package com.kelompok6.hyperaid.data.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.kelompok6.hyperaid.data.model.User

class UserRepository(
    private val firestore: FirebaseFirestore = Firebase.firestore
) {
    fun saveUser(user: User, onResult: (Result<Unit>) -> Unit) {
        firestore.collection("users")
            .document(user.id)
            .set(user)
            .addOnSuccessListener { onResult(Result.success(Unit)) }
            .addOnFailureListener { e -> onResult(Result.failure(e)) }
    }
}
