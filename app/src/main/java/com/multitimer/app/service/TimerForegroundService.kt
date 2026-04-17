package com.multitimer.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.multitimer.app.MainActivity
import com.multitimer.app.R
import com.multitimer.app.data.db.entity.TimerStatus
import com.multitimer.app.data.repository.TimerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class TimerForegroundService : Service() {

    @Inject lateinit var timerRepository: TimerRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val tickerJobs = mutableMapOf<Long, Job>()

    companion object {
        const val CHANNEL_ID = "timer_channel"
        const val CHANNEL_ID_ALERT = "timer_alert_channel"
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
        val text = if (label.isNotEmpty()) "$label finished" else "Timer finished"
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
        // タイマー終了アラート通知（高優先度）
        val alertChannel = NotificationChannel(CHANNEL_ID_ALERT, "Timer Alerts", NotificationManager.IMPORTANCE_HIGH)
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

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
