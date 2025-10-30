package com.kelompok6.hyperaid.ui.screens.reminder

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok6.hyperaid.data.model.Reminder
import com.kelompok6.hyperaid.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReminderViewModel(context: Context) : ViewModel() {
    private val repo = ReminderRepository(context)

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders: StateFlow<List<Reminder>> = _reminders

    init {
        viewModelScope.launch {
            repo.getReminders.collect { list ->
                _reminders.value = list
            }
        }
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
            val updated = _reminders.value + reminder
            repo.saveReminders(updated)
            _reminders.value = updated
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            val updated = _reminders.value - reminder
            repo.saveReminders(updated)
            _reminders.value = updated
        }
    }

    fun updateReminder(old: Reminder, new: Reminder) {
        viewModelScope.launch {
            val updated = _reminders.value.map { if (it.id == old.id) new else it }
            repo.saveReminders(updated)
            _reminders.value = updated
        }
    }
}