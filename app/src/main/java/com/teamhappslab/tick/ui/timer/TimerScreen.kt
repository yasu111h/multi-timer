package com.teamhappslab.tick.ui.timer

import androidx.compose.foundation.BorderStroke
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
import com.teamhappslab.tick.ui.components.TimerCard

@Composable
fun TimerScreen(viewModel: TimerViewModel = hiltViewModel()) {
    val timers by viewModel.timers.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("ADD TIMER")
                }
            }
        }
    }
}
