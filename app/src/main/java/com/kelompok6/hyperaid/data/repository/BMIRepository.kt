package com.kelompok6.hyperaid.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kelompok6.hyperaid.data.model.BMI
import com.kelompok6.hyperaid.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class BMIRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    // Save new BMI
    suspend fun saveBMI(bmi: BMI) { //, onResult: (Result<Unit>) -> Unit) {
        val bmiCollection = db.collection("users")
            .document(bmi.userId)
            .collection("user_bmi")
        bmiCollection.add(bmi).await()
    }

    //  Get all BMI (limit 7 dulu)
    suspend fun getBMI(): Flow<List<BMI>> = flow {
        val currentUserId = auth.currentUser?.uid ?: run {
            emit(emptyList())
            return@flow
        }

        try {
            val snapshot = db.collection("users")
                .document(currentUserId)
                .collection("user_bmi")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(7)
                .get()
                .await()

            val bmiList = snapshot.documents.mapNotNull { it.toObject(BMI::class.java) }
            emit(bmiList)
        } catch (e: Exception) {
            android.util.Log.e("BMIRepo", "Error fetching BMI: ${e.message}")
            emit(emptyList())
        }
    }

    // Get BMI terbaru
    suspend fun getLatestBMI(): Flow<BMI?> = flow {
        val currentUserId = auth.currentUser?.uid ?: run {
            emit(null)
            return@flow
        }

        try {
            val bmiCol = db.collection("users")
                .document(currentUserId)
                .collection("user_bmi")
                .orderBy("date", Query.Direction.DESCENDING) // Gunakan Query yang diimpor
                .limit(1)
                .get().await()

            val latestBMI = bmiCol.documents.firstOrNull()?.toObject(BMI::class.java)
            emit(latestBMI)

        } catch (e: Exception) {
            android.util.Log.e("BMIRepo", "Error fetching latest BMI: ${e.message}")
            // Emit null jika terjadi kesalahan
            emit(null)
        }
    }

        // Get BMI by range of date or something idk???
}
