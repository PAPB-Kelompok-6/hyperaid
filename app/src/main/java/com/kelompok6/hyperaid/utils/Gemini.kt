package com.kelompok6.hyperaid.utils

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object GeminiFoodPredictor {

    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = "AIzaSyCm2YH8qyT4RWTGXUUrtWn_eIN73KBMUHg"
    )

    suspend fun predict(food: String, portion: Int): NutritionResult {
        val prompt = """
            Predict nutrition for: $food
            Portion: $portion
            Return JSON only with fields:
            carbo, protein, serat, lemak (values in gram).
        """.trimIndent()

        val response = model.generateContent(prompt)
        val json = response.text ?: """{"carbo":0,"protein":0,"serat":0,"lemak":0}"""

        Log.d("NutritrackPrediction", "Raw AI Response: $json")

        // Remove code block markers
        val cleanJson = json
            .trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()

        return NutritionResult.fromJson(cleanJson)
    }
}

data class NutritionResult(
    val carbo: Int,
    val protein: Int,
    val serat: Int,
    val lemak: Int
) {
    companion object {
        fun fromJson(json: String): NutritionResult {
            val obj = Json.parseToJsonElement(json).jsonObject
            return NutritionResult(
                carbo = obj["carbo"]?.jsonPrimitive?.doubleOrNull?.toInt() ?: 0,
                protein = obj["protein"]?.jsonPrimitive?.doubleOrNull?.toInt() ?: 0,
                serat = obj["serat"]?.jsonPrimitive?.doubleOrNull?.toInt() ?: 0,
                lemak = obj["lemak"]?.jsonPrimitive?.doubleOrNull?.toInt() ?: 0
            )
        }
    }
}
