package com.multitimer.app.ui.stopwatch

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.multitimer.app.data.db.entity.StopwatchStatus
import com.multitimer.app.ui.components.BulkActionBar
import com.multitimer.app.ui.components.StopwatchCard

@Composable
fun StopwatchScreen(viewModel: StopwatchViewModel = hiltViewModel()) {
    val stopwatches by viewModel.stopwatches.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(stopwatches, key = { it.id }) { sw ->
                StopwatchCard(
                    stopwatch = sw,
                    lapsFlow = viewModel.getLaps(sw.id),
                    onStart = { viewModel.startStopwatch(sw.id) },
                    onPause = { viewModel.pauseStopwatch(sw.id) },
                    onReset = { viewModel.resetStopwatch(sw.id) },
                    onLap = { viewModel.recordLap(sw.id) },
                    onDelete = { viewModel.deleteStopwatch(sw.id) },
                    onLabelSet = { label -> viewModel.updateLabel(sw.id, label) }
                )
            }
            if (stopwatches.size < 5) {
                item {
                    OutlinedButton(
                        onClick = { viewModel.addStopwatch() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("ストップウォッチを追加")
                    }
                }
            }
        }

        BulkActionBar(
            hasIdle = stopwatches.any { it.status == StopwatchStatus.IDLE },
            hasRunning = stopwatches.any { it.status == StopwatchStatus.RUNNING },
            hasPaused = stopwatches.any { it.status == StopwatchStatus.PAUSED },
            onStartAll = { viewModel.startAll() },
            onPauseAll = { viewModel.pauseAll() },
            onResetAll = { viewModel.resetAll() }
        )
    }
}
