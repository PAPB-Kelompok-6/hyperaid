    package com.kelompok6.hyperaid.data.repository

    import android.util.Log
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.firestore.FirebaseFirestore
    import com.kelompok6.hyperaid.data.model.BMI
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

        // ▶️ Get BMI by ID
        fun getBMI(): Flow<List<BMI>> = flow {
            val currentUserId = auth.currentUser?.uid ?: run {
                emit(emptyList())
                return@flow
            }

            try {
                val snapshot = db.collection("users")
                    .document(currentUserId)
                    .collection("user_bmi")
                    .get()
                    .await()

                val bmiList = snapshot.documents.mapNotNull { it.toObject(BMI::class.java) }
                emit(bmiList)
            } catch (e: Exception) {
                android.util.Log.e("BMIRepo", "Error fetching BMI: ${e.message}")
                emit(emptyList()) // Pastikan selalu emit sesuatu meskipun error
            }
        }

        // Get BMI by range of date or something idk???
    }
