package com.multitimer.app.data.db.dao

import androidx.room.*
import com.multitimer.app.data.db.entity.TimerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Query("SELECT * FROM timers ORDER BY sort_order ASC")
    fun getAllTimers(): Flow<List<TimerEntity>>

    @Query("SELECT * FROM timers WHERE id = :id")
    suspend fun getTimerById(id: Long): TimerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimer(timer: TimerEntity): Long

    @Update
    suspend fun updateTimer(timer: TimerEntity)

    @Query("DELETE FROM timers WHERE id = :id")
    suspend fun deleteTimerById(id: Long)

    @Query("SELECT * FROM timers WHERE status = 'RUNNING'")
    suspend fun getRunningTimers(): List<TimerEntity>

    @Query("SELECT COUNT(*) FROM timers")
    suspend fun getTimerCount(): Int

    @Query("UPDATE timers SET sort_order = :order WHERE id = :id")
    suspend fun updateSortOrder(id: Long, order: Int)
}
