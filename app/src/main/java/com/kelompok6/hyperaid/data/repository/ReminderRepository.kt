package com.kelompok6.hyperaid.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.kelompok6.hyperaid.data.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("reminders_pref")

class ReminderRepository(private val context: Context) {
    private val REMINDER_KEY = stringPreferencesKey("reminders")

    private val gson = Gson()

    // 🔹 Simpan list reminder ke DataS  tore
    suspend fun saveReminders(reminders: List<Reminder>) {
        val json = gson.toJson(reminders)
        context.dataStore.edit { prefs ->
            prefs[REMINDER_KEY] = json
        }
    }

    // 🔹 Ambil list reminder dari DataStore
    val getReminders: Flow<List<Reminder>> = context.dataStore.data.map { prefs ->
        val json = prefs[REMINDER_KEY] ?: "[]"
        val type = object : TypeToken<List<Reminder>>() {}.type
        gson.fromJson(json, type)
    }
}
