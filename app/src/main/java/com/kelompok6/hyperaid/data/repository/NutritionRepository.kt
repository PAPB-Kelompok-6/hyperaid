package com.kelompok6.hyperaid.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.kelompok6.hyperaid.data.model.NutritionData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId

class NutritionRepository(
    private val firestore: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    private fun userCollection(): CollectionReference {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("User must be logged in")

        return firestore.collection("users")
            .document(uid)
            .collection("nutritrack")
    }

    suspend fun getNutritionDataById(id: String): Flow<NutritionData?> = callbackFlow {
        val listener = userCollection()
            .document(id)
            .addSnapshotListener { snap, _ ->
                trySend(snap?.toObject(NutritionData::class.java))
            }

        awaitClose { listener.remove() }
    }

    suspend fun getTodayNutritionList(): Flow<List<NutritionData>> = callbackFlow {
        val todayStr = LocalDate.now().toString()

        val listener = userCollection()
            .whereEqualTo("date", todayStr)
            .addSnapshotListener { snap, _ ->
                val dataList = snap?.documents
                    ?.mapNotNull { it.toObject(NutritionData::class.java) } ?: emptyList()
                Log.d("NutritionRepo", "Docs found: ${snap?.documents?.size}")
                trySend(dataList)
            }

        awaitClose { listener.remove() }
    }

    fun getAllNutritionData(): Flow<List<NutritionData>> = callbackFlow {
        val listener = userCollection()
            .orderBy("date", Query.Direction.DESCENDING) // optional, for chronological order
            .addSnapshotListener { snap, _ ->
                Log.d("NutritionRepo", "Docs found: ${snap?.documents?.size}")
                val dataList = snap?.documents
                    ?.mapNotNull { it.toObject(NutritionData::class.java) }
                    ?: emptyList()
                trySend(dataList)
            }

        awaitClose { listener.remove() }
    }

    suspend fun saveNutritionData(data: NutritionData) {
        val doc =
            if (data.id.isBlank()) userCollection().document() else userCollection().document(data.id)

        val finalData = data.copy(id = doc.id)

        doc.set(finalData).await()
    }
}
