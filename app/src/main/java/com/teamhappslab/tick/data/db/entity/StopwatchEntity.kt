package com.teamhappslab.tick.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class StopwatchStatus { IDLE, RUNNING, PAUSED }

@Entity(tableName = "stopwatches")
data class StopwatchEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val label: String = "",
    @ColumnInfo(name = "elapsed_millis") val elapsedMillis: Long = 0L,
    val status: StopwatchStatus = StopwatchStatus.IDLE,
    @ColumnInfo(name = "started_at") val startedAt: Long? = null,
    @ColumnInfo(name = "paused_at") val pausedAt: Long? = null,
    @ColumnInfo(name = "current_lap_start_millis") val currentLapStartMillis: Long = 0L,
    @ColumnInfo(name = "lap_count") val lapCount: Int = 0,
    @ColumnInfo(name = "sort_order") val sortOrder: Int = 0,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)
