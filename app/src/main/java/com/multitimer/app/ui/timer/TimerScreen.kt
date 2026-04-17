package com.multitimer.app.ui.timer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.multitimer.app.data.db.entity.TimerStatus
import com.multitimer.app.ui.components.BulkActionBar
import com.multitimer.app.ui.components.TimerCard

@Composable
fun TimerScreen(viewModel: TimerViewModel = hiltViewModel()) {
    val timers by viewModel.timers.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(timers, key = { it.id }) { timer ->
                TimerCard(
                    timer = timer,
                    onStart = { viewModel.startTimer(timer.id) },
                    onPause = { viewModel.pauseTimer(timer.id) },
                    onResume = { viewModel.resumeTimer(timer.id) },
                    onReset = { viewModel.resetTimer(timer.id) },
                    onDelete = { viewModel.deleteTimer(timer.id) },
                    onTimeSet = { millis -> viewModel.updateTimerTime(timer.id, millis) },
                    onLabelSet = { label -> viewModel.updateTimerLabel(timer.id, label) }
                )
            }
            if (timers.size < 5) {
                item {
                    OutlinedButton(
                        onClick = { viewModel.addTimer() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("タイマーを追加")
                    }
                }
            }
        }

        BulkActionBar(
            hasIdle = timers.any { it.status == TimerStatus.IDLE },
            hasRunning = timers.any { it.status == TimerStatus.RUNNING },
            hasPaused = timers.any { it.status == TimerStatus.PAUSED },
            onStartAll = { viewModel.startAll() },
            onPauseAll = { viewModel.pauseAll() },
            onResetAll = { viewModel.resetAll() }
        )
    }
}
