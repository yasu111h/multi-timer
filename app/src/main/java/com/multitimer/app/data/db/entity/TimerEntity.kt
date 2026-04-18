package com.multitimer.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TimerStatus { IDLE, RUNNING, PAUSED, FINISHED }

@Entity(tableName = "timers")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val label: String = "",
    @ColumnInfo(name = "total_millis") val totalMillis: Long = 10_000L,
    @ColumnInfo(name = "remaining_millis") val remainingMillis: Long = 10_000L,
    val status: TimerStatus = TimerStatus.IDLE,
    @ColumnInfo(name = "started_at") val startedAt: Long? = null,
    @ColumnInfo(name = "paused_at") val pausedAt: Long? = null,
    @ColumnInfo(name = "sort_order") val sortOrder: Int = 0,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)
