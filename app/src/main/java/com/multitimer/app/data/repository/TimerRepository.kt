package com.multitimer.app.data.repository

import com.multitimer.app.data.db.dao.TimerDao
import com.multitimer.app.data.db.entity.TimerEntity
import com.multitimer.app.data.db.entity.TimerStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerRepository @Inject constructor(private val timerDao: TimerDao) {

    fun getAllTimers(): Flow<List<TimerEntity>> = timerDao.getAllTimers()

    suspend fun getTimerById(id: Long): TimerEntity? = timerDao.getTimerById(id)

    suspend fun addTimer(): Long {
        val count = timerDao.getTimerCount()
        return timerDao.insertTimer(TimerEntity(label = "Timer ${count + 1}", sortOrder = count))
    }

    suspend fun deleteTimer(id: Long) = timerDao.deleteTimerById(id)

    suspend fun getTimerCount(): Int = timerDao.getTimerCount()

    suspend fun updateTimer(timer: TimerEntity) = timerDao.updateTimer(timer)

    suspend fun startTimer(id: Long) {
        val timer = timerDao.getTimerById(id) ?: return
        timerDao.updateTimer(
            timer.copy(
                status = TimerStatus.RUNNING,
                startedAt = System.currentTimeMillis(),
                pausedAt = null,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun pauseTimer(id: Long) {
        val timer = timerDao.getTimerById(id) ?: return
        val now = System.currentTimeMillis()
        val elapsed = if (timer.startedAt != null) now - timer.startedAt else 0L
        val newRemaining = (timer.remainingMillis - elapsed).coerceAtLeast(0)
        timerDao.updateTimer(
            timer.copy(
                status = TimerStatus.PAUSED,
                remainingMillis = newRemaining,
                startedAt = null,
                pausedAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun resetTimer(id: Long) {
        val timer = timerDao.getTimerById(id) ?: return
        timerDao.updateTimer(
            timer.copy(
                status = TimerStatus.IDLE,
                remainingMillis = timer.totalMillis,
                startedAt = null,
                pausedAt = null,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun finishTimer(id: Long) {
        val timer = timerDao.getTimerById(id) ?: return
        timerDao.updateTimer(
            timer.copy(
                status = TimerStatus.FINISHED,
                remainingMillis = 0,
                startedAt = null,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateTimerTime(id: Long, totalMillis: Long) {
        val timer = timerDao.getTimerById(id) ?: return
        timerDao.updateTimer(
            timer.copy(
                totalMillis = totalMillis,
                remainingMillis = totalMillis,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateTimerLabel(id: Long, label: String) {
        val timer = timerDao.getTimerById(id) ?: return
        timerDao.updateTimer(timer.copy(label = label, updatedAt = System.currentTimeMillis()))
    }

    suspend fun restoreRunningTimers() {
        val runningTimers = timerDao.getRunningTimers()
        val now = System.currentTimeMillis()
        runningTimers.forEach { timer ->
            val startedAt = timer.startedAt ?: return@forEach
            val elapsed = now - startedAt
            val newRemaining = (timer.remainingMillis - elapsed).coerceAtLeast(0)
            if (newRemaining <= 0) {
                timerDao.updateTimer(timer.copy(status = TimerStatus.FINISHED, remainingMillis = 0, startedAt = null, updatedAt = now))
            } else {
                timerDao.updateTimer(timer.copy(remainingMillis = newRemaining, startedAt = now, updatedAt = now))
            }
        }
    }
}
