package com.multitimer.app.data.db.dao

import androidx.room.*
import com.multitimer.app.data.db.entity.StopwatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StopwatchDao {
    @Query("SELECT * FROM stopwatches ORDER BY sort_order ASC")
    fun getAllStopwatches(): Flow<List<StopwatchEntity>>

    @Query("SELECT * FROM stopwatches WHERE id = :id")
    suspend fun getStopwatchById(id: Long): StopwatchEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStopwatch(stopwatch: StopwatchEntity): Long

    @Update
    suspend fun updateStopwatch(stopwatch: StopwatchEntity)

    @Query("DELETE FROM stopwatches WHERE id = :id")
    suspend fun deleteStopwatchById(id: Long)

    @Query("SELECT COUNT(*) FROM stopwatches")
    suspend fun getStopwatchCount(): Int

    @Query("SELECT * FROM stopwatches WHERE status = 'RUNNING'")
    suspend fun getRunningStopwatches(): List<StopwatchEntity>
}
