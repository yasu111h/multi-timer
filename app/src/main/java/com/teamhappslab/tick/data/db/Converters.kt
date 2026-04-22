package com.teamhappslab.tick.data.db

import androidx.room.TypeConverter
import com.teamhappslab.tick.data.db.entity.TimerStatus
import com.teamhappslab.tick.data.db.entity.StopwatchStatus

class Converters {
    @TypeConverter fun fromTimerStatus(s: TimerStatus): String = s.name
    @TypeConverter fun toTimerStatus(v: String): TimerStatus = TimerStatus.valueOf(v)
    @TypeConverter fun fromStopwatchStatus(s: StopwatchStatus): String = s.name
    @TypeConverter fun toStopwatchStatus(v: String): StopwatchStatus = StopwatchStatus.valueOf(v)
}
