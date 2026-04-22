package com.teamhappslab.tick.ui.timer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamhappslab.tick.data.db.entity.TimerEntity
import com.teamhappslab.tick.data.db.entity.TimerStatus
import com.teamhappslab.tick.data.repository.TimerRepository
import com.teamhappslab.tick.service.TimerForegroundService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val repository: TimerRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val timers = repository.getAllTimers()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addTimer() = viewModelScope.launch {
        if (repository.getTimerCount() < 5) repository.addTimer()
    }

    fun deleteTimer(id: Long) = viewModelScope.launch {
        repository.resetTimer(id)
        repository.deleteTimer(id)
        context.startService(TimerForegroundService.resetIntent(context, id))
    }

    fun startTimer(id: Long) = viewModelScope.launch {
        repository.startTimer(id)
        context.startForegroundService(TimerForegroundService.startIntent(context, id))
    }

    fun pauseTimer(id: Long) = viewModelScope.launch {
        repository.pauseTimer(id)
        context.startService(TimerForegroundService.pauseIntent(context, id))
    }

    fun resumeTimer(id: Long) = startTimer(id)

    fun resetTimer(id: Long) = viewModelScope.launch {
        repository.resetTimer(id)
        context.startService(TimerForegroundService.resetIntent(context, id))
    }

    fun updateTimerTime(id: Long, totalMillis: Long) = viewModelScope.launch {
        repository.updateTimerTime(id, totalMillis)
    }

    fun updateTimerLabel(id: Long, label: String) = viewModelScope.launch {
        repository.updateTimerLabel(id, label)
    }

    fun startAll() = viewModelScope.launch {
        timers.value.filter { it.status == TimerStatus.IDLE || it.status == TimerStatus.PAUSED }
            .forEach { startTimer(it.id) }
    }

    fun pauseAll() = viewModelScope.launch {
        timers.value.filter { it.status == TimerStatus.RUNNING }.forEach { pauseTimer(it.id) }
    }

    fun resetAll() = viewModelScope.launch {
        timers.value.forEach { resetTimer(it.id) }
    }
}
