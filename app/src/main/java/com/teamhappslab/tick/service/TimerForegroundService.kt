package com.teamhappslab.tick.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import com.teamhappslab.tick.MainActivity
import com.teamhappslab.tick.R
import com.teamhappslab.tick.data.db.entity.TimerStatus
import com.teamhappslab.tick.data.repository.SettingsRepository
import com.teamhappslab.tick.data.repository.TimerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class TimerForegroundService : Service() {

    @Inject lateinit var timerRepository: TimerRepository
    @Inject lateinit var settingsRepository: SettingsRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val tickerJobs = mutableMapOf<Long, Job>()

    companion object {
        const val CHANNEL_ID = "timer_channel"
        // チャンネルIDを変更して端末上の古いチャンネルを上書き（以前の設定が残っている場合の対処）
        const val CHANNEL_ID_ALERT = "timer_alert_channel_v4"
        const val NOTIFICATION_ID = 1
        const val ACTION_START = "action.START"
        const val ACTION_PAUSE = "action.PAUSE"
        const val ACTION_RESET = "action.RESET"
        const val EXTRA_TIMER_ID = "extra.TIMER_ID"

        fun startIntent(context: Context, timerId: Long) =
            Intent(context, TimerForegroundService::class.java).apply {
                action = ACTION_START
                putExtra(EXTRA_TIMER_ID, timerId)
            }

        fun pauseIntent(context: Context, timerId: Long) =
            Intent(context, TimerForegroundService::class.java).apply {
                action = ACTION_PAUSE
                putExtra(EXTRA_TIMER_ID, timerId)
            }

        fun resetIntent(context: Context, timerId: Long) =
            Intent(context, TimerForegroundService::class.java).apply {
                action = ACTION_RESET
                putExtra(EXTRA_TIMER_ID, timerId)
            }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildSilentNotification("Running in background"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val timerId = intent?.getLongExtra(EXTRA_TIMER_ID, -1L) ?: -1L
        when (intent?.action) {
            ACTION_START -> if (timerId != -1L) startTicker(timerId)
            ACTION_PAUSE -> if (timerId != -1L) stopTicker(timerId)
            ACTION_RESET -> if (timerId != -1L) stopTicker(timerId)
        }
        return START_STICKY
    }

    private fun startTicker(timerId: Long) {
        tickerJobs[timerId]?.cancel()
        tickerJobs[timerId] = serviceScope.launch {
            while (isActive) {
                delay(500L)
                val timer = timerRepository.getTimerById(timerId) ?: break
                if (timer.status != TimerStatus.RUNNING) break
                val startedAt = timer.startedAt ?: break
                val elapsed = System.currentTimeMillis() - startedAt
                val newRemaining = (timer.remainingMillis - elapsed).coerceAtLeast(0)
                if (newRemaining <= 0) {
                    timerRepository.finishTimer(timerId)
                    showTimerFinishedNotification(timer.label)
                    val soundEnabled = settingsRepository.isSoundEnabled()
                    val vibrationEnabled = settingsRepository.isVibrationEnabled()
                    withContext(Dispatchers.Main) {
                        if (soundEnabled) playCompletionSound()
                        if (vibrationEnabled) vibrate()
                        // 音が鳴り終わるまでサービスを生かしておく
                        // MediaPlayer の onCompletion でリリースされる
                    }
                    // 音の再生時間を確保してからサービスを停止（最低3秒）
                    delay(3000L)
                    break
                }
            }
            checkAndStopService()
        }
    }

    private fun stopTicker(timerId: Long) {
        tickerJobs[timerId]?.cancel()
        tickerJobs.remove(timerId)
        checkAndStopService()
    }

    private fun checkAndStopService() {
        if (tickerJobs.isEmpty()) stopSelf()
    }

    private fun showTimerFinishedNotification(label: String) {
        val manager = getSystemService(NotificationManager::class.java)
        val text = if (label.isNotEmpty()) "$label - finished" else "Timer finished"
        val notification = NotificationCompat.Builder(this, CHANNEL_ID_ALERT)
            .setContentTitle("TICK")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    this, 0,
                    Intent(this, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()
        manager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun createNotificationChannel() {
        val manager = getSystemService(NotificationManager::class.java)
        // サービス動作中の通知（サイレント・低優先度）
        val silentChannel = NotificationChannel(CHANNEL_ID, "Timer Service", NotificationManager.IMPORTANCE_LOW).apply {
            setSound(null, null)
            enableVibration(false)
        }
        // タイマー終了アラート通知（音はMediaPlayerで制御するためチャンネルは無音）
        val alertChannel = NotificationChannel(
            CHANNEL_ID_ALERT, "Timer Alerts", NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(null, null)   // 通知チャンネルの音を無効（MediaPlayerで1回だけ鳴らす）
            enableVibration(false) // バイブもサービス側で制御
        }
        manager.createNotificationChannel(silentChannel)
        manager.createNotificationChannel(alertChannel)
    }

    private fun buildSilentNotification(text: String) =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("TICK")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setSilent(true)
            .setOngoing(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    this, 0,
                    Intent(this, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()

    private fun playCompletionSound() {
        try {
            val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.alarm_sound)?.apply {
                // USAGE_ALARM でマナーモード（サイレント）中でも鳴らす
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                setOnCompletionListener {
                    android.util.Log.d("TICK_SOUND", "再生完了")
                    it.release()
                }
                start()
            }
            android.util.Log.d("TICK_SOUND", "再生開始: ${mediaPlayer != null}")
        } catch (e: Exception) {
            android.util.Log.e("TICK_SOUND", "再生失敗: ${e.message}", e)
        }
    }

    private fun vibrate() {
        try {
            val pattern = longArrayOf(0, 400, 200, 400, 200, 400)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vm.defaultVibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createWaveform(pattern, -1))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(pattern, -1)
                }
            }
        } catch (e: Exception) {
            // バイブレーション失敗時は無視
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
