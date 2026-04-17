package com.multitimer.app.data.db.dao

import androidx.room.*
import com.multitimer.app.data.db.entity.PresetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {
    @Query("SELECT * FROM presets ORDER BY sort_order ASC")
    fun getAllPresets(): Flow<List<PresetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreset(preset: PresetEntity): Long

    @Query("DELETE FROM presets WHERE id = :id")
    suspend fun deletePresetById(id: Long)

    @Query("SELECT COUNT(*) FROM presets")
    suspend fun getPresetCount(): Int

    @Update
    suspend fun updatePreset(preset: PresetEntity)
}
