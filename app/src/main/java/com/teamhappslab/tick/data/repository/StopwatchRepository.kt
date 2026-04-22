package com.teamhappslab.tick.data.repository

import com.teamhappslab.tick.data.db.dao.LapDao
import com.teamhappslab.tick.data.db.dao.StopwatchDao
import com.teamhappslab.tick.data.db.entity.LapEntity
import com.teamhappslab.tick.data.db.entity.StopwatchEntity
import com.teamhappslab.tick.data.db.entity.StopwatchStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopwatchRepository @Inject constructor(
    private val stopwatchDao: StopwatchDao,
    private val lapDao: LapDao
) {
    fun getAllStopwatches(): Flow<List<StopwatchEntity>> = stopwatchDao.getAllStopwatches()

    suspend fun getStopwatchById(id: Long): StopwatchEntity? = stopwatchDao.getStopwatchById(id)

    fun getLaps(stopwatchId: Long) = lapDao.getLapsByStopwatchId(stopwatchId)

    suspend fun addStopwatch(): Long {
        val count = stopwatchDao.getStopwatchCount()
        return stopwatchDao.insertStopwatch(StopwatchEntity(label = "Stopwatch ${count + 1}", sortOrder = count))
    }

    suspend fun deleteStopwatch(id: Long) {
        lapDao.deleteLapsByStopwatchId(id)
        stopwatchDao.deleteStopwatchById(id)
    }

    suspend fun getStopwatchCount(): Int = stopwatchDao.getStopwatchCount()

    suspend fun startStopwatch(id: Long) {
        val sw = stopwatchDao.getStopwatchById(id) ?: return
        stopwatchDao.updateStopwatch(
            sw.copy(status = StopwatchStatus.RUNNING, startedAt = System.currentTimeMillis(), updatedAt = System.currentTimeMillis())
        )
    }

    suspend fun pauseStopwatch(id: Long) {
        val sw = stopwatchDao.getStopwatchById(id) ?: return
        val now = System.currentTimeMillis()
        val elapsed = if (sw.startedAt != null) now - sw.startedAt else 0L
        stopwatchDao.updateStopwatch(
            sw.copy(
                status = StopwatchStatus.PAUSED,
                elapsedMillis = sw.elapsedMillis + elapsed,
                startedAt = null,
                pausedAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun resetStopwatch(id: Long) {
        val sw = stopwatchDao.getStopwatchById(id) ?: return
        lapDao.deleteLapsByStopwatchId(id)
        stopwatchDao.updateStopwatch(
            sw.copy(
                status = StopwatchStatus.IDLE,
                elapsedMillis = 0L,
                startedAt = null,
                pausedAt = null,
                currentLapStartMillis = 0L,
                lapCount = 0,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun recordLap(id: Long) {
        val sw = stopwatchDao.getStopwatchById(id) ?: return
        if (sw.status != StopwatchStatus.RUNNING) return
        val now = System.currentTimeMillis()
        val elapsed = sw.elapsedMillis + (now - (sw.startedAt ?: now))
        val lapMillis = elapsed - sw.currentLapStartMillis
        val newLapCount = sw.lapCount + 1
        lapDao.insertLap(
            LapEntity(
                stopwatchId = id,
                lapNumber = newLapCount,
                lapMillis = lapMillis,
                cumulativeMillis = elapsed
            )
        )
        stopwatchDao.updateStopwatch(
            sw.copy(
                lapCount = newLapCount,
                currentLapStartMillis = elapsed,
                startedAt = now,
                elapsedMillis = elapsed,
                updatedAt = now
            )
        )
    }

    suspend fun updateLabel(id: Long, label: String) {
        val sw = stopwatchDao.getStopwatchById(id) ?: return
        stopwatchDao.updateStopwatch(sw.copy(label = label, updatedAt = System.currentTimeMillis()))
    }
}
