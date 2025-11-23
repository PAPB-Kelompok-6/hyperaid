package com.kelompok6.hyperaid.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.kelompok6.hyperaid.data.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReminder(reminder: Reminder)

    @Query("SELECT * FROM reminder_table ORDER BY id desc")
    fun getAllReminder(): Flow<List<Reminder>>

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}