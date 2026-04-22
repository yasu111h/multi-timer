package com.teamhappslab.tick.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val KEEP_SCREEN_ON = booleanPreferencesKey("keep_screen_on")
    }

    val vibrationEnabled: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[Keys.VIBRATION_ENABLED] ?: true }

    val soundEnabled: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[Keys.SOUND_ENABLED] ?: true }

    val keepScreenOn: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[Keys.KEEP_SCREEN_ON] ?: true }

    suspend fun setVibrationEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[Keys.VIBRATION_ENABLED] = enabled }
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[Keys.SOUND_ENABLED] = enabled }
    }

    suspend fun setKeepScreenOn(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[Keys.KEEP_SCREEN_ON] = enabled }
    }

    suspend fun isVibrationEnabled(): Boolean =
        vibrationEnabled.first()

    suspend fun isSoundEnabled(): Boolean =
        soundEnabled.first()
}
