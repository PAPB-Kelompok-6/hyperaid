package com.kelompok6.hyperaid.ui.screens.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kelompok6.hyperaid.data.model.Reminder
import com.kelompok6.hyperaid.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReminderViewModel(private val repos: ReminderRepository) : ViewModel() {
//    private val allReminders = repository.allReminder

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders: StateFlow<List<Reminder>> = _reminders

    init {
        loadReminders()
    }

    private fun loadReminders() {
        viewModelScope.launch {
            repos.allReminder.collectLatest { list ->
                _reminders.value = list
            }
        }
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
            repos.saveReminders(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            repos.delete(reminder)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            repos.update(reminder)
        }
    }
}

class ReminderViewModelFactory(
    private val repository: ReminderRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReminderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}