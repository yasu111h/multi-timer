package com.teamhappslab.tick.data.db.dao

import androidx.room.*
import com.teamhappslab.tick.data.db.entity.LapEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LapDao {
    @Query("SELECT * FROM laps WHERE stopwatch_id = :stopwatchId ORDER BY lap_number ASC")
    fun getLapsByStopwatchId(stopwatchId: Long): Flow<List<LapEntity>>

    @Insert
    suspend fun insertLap(lap: LapEntity): Long

    @Query("DELETE FROM laps WHERE stopwatch_id = :stopwatchId")
    suspend fun deleteLapsByStopwatchId(stopwatchId: Long)

    @Query("SELECT COUNT(*) FROM laps WHERE stopwatch_id = :stopwatchId")
    suspend fun getLapCount(stopwatchId: Long): Int
}
