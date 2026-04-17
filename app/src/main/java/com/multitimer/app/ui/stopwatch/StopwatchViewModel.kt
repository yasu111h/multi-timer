package com.multitimer.app.ui.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multitimer.app.data.db.entity.StopwatchStatus
import com.multitimer.app.data.repository.StopwatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val repository: StopwatchRepository
) : ViewModel() {

    val stopwatches = repository.getAllStopwatches()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun getLaps(id: Long) = repository.getLaps(id)

    fun addStopwatch() = viewModelScope.launch {
        if (repository.getStopwatchCount() < 5) repository.addStopwatch()
    }

    fun deleteStopwatch(id: Long) = viewModelScope.launch { repository.deleteStopwatch(id) }

    fun startStopwatch(id: Long) = viewModelScope.launch { repository.startStopwatch(id) }

    fun pauseStopwatch(id: Long) = viewModelScope.launch { repository.pauseStopwatch(id) }

    fun resetStopwatch(id: Long) = viewModelScope.launch { repository.resetStopwatch(id) }

    fun recordLap(id: Long) = viewModelScope.launch { repository.recordLap(id) }

    fun updateLabel(id: Long, label: String) = viewModelScope.launch { repository.updateLabel(id, label) }

    fun startAll() = viewModelScope.launch {
        stopwatches.value.filter { it.status == StopwatchStatus.IDLE || it.status == StopwatchStatus.PAUSED }
            .forEach { startStopwatch(it.id) }
    }

    fun pauseAll() = viewModelScope.launch {
        stopwatches.value.filter { it.status == StopwatchStatus.RUNNING }.forEach { pauseStopwatch(it.id) }
    }

    fun resetAll() = viewModelScope.launch {
        stopwatches.value.forEach { resetStopwatch(it.id) }
    }
}
