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
import com.multitimer.app.ui.components.StopwatchCard

@Composable
fun StopwatchScreen(viewModel: StopwatchViewModel = hiltViewModel()) {
    val stopwatches by viewModel.stopwatches.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("ADD STOPWATCH")
                }
            }
        }
    }
}
