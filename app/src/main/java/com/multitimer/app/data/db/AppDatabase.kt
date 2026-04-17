package com.multitimer.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.multitimer.app.data.db.dao.*
import com.multitimer.app.data.db.entity.*

@Database(
    entities = [TimerEntity::class, StopwatchEntity::class, LapEntity::class, PresetEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
    abstract fun stopwatchDao(): StopwatchDao
    abstract fun lapDao(): LapDao
    abstract fun presetDao(): PresetDao
}
