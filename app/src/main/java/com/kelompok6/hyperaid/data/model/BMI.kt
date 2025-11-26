package com.kelompok6.hyperaid.data.model

import java.util.Date
import com.google.firebase.firestore.DocumentId

data class BMI(
    @DocumentId val id: String? = null,
    val userId: String,

    val date: Date? = null,
    val height: Int,
    val weight: Int,
    val age: Int,
) {
    val bmi: Float = weight * 10000f / (height * height)
}
