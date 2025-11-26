package com.kelompok6.hyperaid.data.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.kelompok6.hyperaid.data.model.User
import com.kelompok6.hyperaid.utils.FirestoreCollections
import kotlinx.coroutines.suspendCancellableCoroutine

class UserRepository(
    private val firestore: FirebaseFirestore = Firebase.firestore
) {
    fun saveUser(user: User, onResult: (Result<Unit>) -> Unit) {
        firestore.collection(FirestoreCollections.USERS)
            .document(user.id)
            .set(user)
            .addOnSuccessListener { onResult(Result.success(Unit)) }
            .addOnFailureListener { e -> onResult(Result.failure(e)) }
    }

    fun getUser(uid: String, onResult: (Result<User>) -> Unit) {
        firestore.collection(FirestoreCollections.USERS)
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    if (user != null) {
                        onResult(Result.success(user))
                    } else {
                        onResult(Result.failure(Exception("Failed to parse user data")))
                    }
                } else {
                    onResult(Result.failure(Exception("User not found")))
                }
            }
            .addOnFailureListener { e ->
                onResult(Result.failure(e))
            }
    }

    suspend fun isLanguageMissing(userId: String): Boolean {
        return suspendCancellableCoroutine { cont ->
            getUser(userId) { result ->
                result.onSuccess { user ->
                    cont.resume(user.languagePreference.isNullOrEmpty()) { cause, _, _ -> }
                }.onFailure {
                    cont.resume(true) { cause, _, _ -> } // missing or error = need language
                }
            }
        }
    }
}
