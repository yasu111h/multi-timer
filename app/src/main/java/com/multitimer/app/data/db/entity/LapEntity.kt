package com.multitimer.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "laps",
    foreignKeys = [ForeignKey(
        entity = StopwatchEntity::class,
        parentColumns = ["id"],
        childColumns = ["stopwatch_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["stopwatch_id"])]
)
data class LapEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "stopwatch_id") val stopwatchId: Long,
    @ColumnInfo(name = "lap_number") val lapNumber: Int,
    @ColumnInfo(name = "lap_millis") val lapMillis: Long,
    @ColumnInfo(name = "cumulative_millis") val cumulativeMillis: Long,
    @ColumnInfo(name = "recorded_at") val recordedAt: Long = System.currentTimeMillis()
)
