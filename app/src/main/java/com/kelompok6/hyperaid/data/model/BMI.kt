package com.kelompok6.hyperaid.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class BMI(
    @DocumentId val id: String? = null,
    val userId: String = "",
    val date: Timestamp? = Timestamp.now(),
    val height: Int = 0,
    val weight: Int = 0,
    val age: Int = 0,
) {
    val bmi: Float
        get() = if (height > 0) weight * 10000f / (height * height) else 0f
}
