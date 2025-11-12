package com.kelompok6.hyperaid.data.repository

import com.kelompok6.hyperaid.data.dao.ReminderDAO
import com.kelompok6.hyperaid.data.model.Reminder
import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val reminderDAO: ReminderDAO) {
    val allReminder: Flow<List<Reminder>> = reminderDAO.getAllReminder()

    // 🔹 Simpan list reminder ke Room
    suspend fun saveReminders(reminder: Reminder) {
        reminderDAO.insertReminder(reminder)
    }

    // 🔹 Ambil list reminder dari Room
    suspend fun update(reminder: Reminder) {
        reminderDAO.updateReminder(reminder)
    }
    // Fungsi untuk Delete: dipanggil dalam Coroutine
    suspend fun delete(reminder: Reminder) {
        reminderDAO.deleteReminder(reminder)
    }
}
