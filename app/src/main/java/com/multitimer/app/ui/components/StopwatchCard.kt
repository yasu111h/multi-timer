package com.multitimer.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multitimer.app.data.db.entity.LapEntity
import com.multitimer.app.data.db.entity.StopwatchEntity
import com.multitimer.app.data.db.entity.StopwatchStatus
import kotlinx.coroutines.flow.Flow

@Composable
fun StopwatchCard(
    stopwatch: StopwatchEntity,
    lapsFlow: Flow<List<LapEntity>>,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onLap: () -> Unit,
    onDelete: () -> Unit,
    onLabelSet: (String) -> Unit
) {
    val laps by lapsFlow.collectAsState(initial = emptyList())
    var showLabelDialog by remember { mutableStateOf(false) }
    var displayMillis by remember(stopwatch.id, stopwatch.status) {
        mutableLongStateOf(stopwatch.elapsedMillis)
    }

    LaunchedEffect(stopwatch.id, stopwatch.status, stopwatch.startedAt, stopwatch.elapsedMillis) {
        if (stopwatch.status == StopwatchStatus.RUNNING && stopwatch.startedAt != null) {
            while (true) {
                displayMillis = stopwatch.elapsedMillis + (System.currentTimeMillis() - stopwatch.startedAt)
                kotlinx.coroutines.delay(10)
            }
        } else {
            displayMillis = stopwatch.elapsedMillis
        }
    }

    val cardColor = when (stopwatch.status) {
        StopwatchStatus.RUNNING -> MaterialTheme.colorScheme.primaryContainer
        StopwatchStatus.PAUSED -> MaterialTheme.colorScheme.secondaryContainer
        StopwatchStatus.IDLE -> MaterialTheme.colorScheme.surface
    }

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cardColor)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stopwatch.label.ifEmpty { "ラベルなし" },
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.clickable { showLabelDialog = true }
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "削除", modifier = Modifier.size(16.dp))
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = formatStopwatchMillis(displayMillis),
                fontSize = 48.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                when (stopwatch.status) {
                    StopwatchStatus.IDLE -> {
                        Button(onClick = onStart, modifier = Modifier.weight(1f)) { Text("スタート") }
                    }
                    StopwatchStatus.RUNNING -> {
                        Button(onClick = onPause, modifier = Modifier.weight(1f)) { Text("一時停止") }
                        OutlinedButton(onClick = onLap, modifier = Modifier.weight(1f)) { Text("ラップ") }
                        OutlinedButton(onClick = onReset, modifier = Modifier.weight(1f)) { Text("リセット") }
                    }
                    StopwatchStatus.PAUSED -> {
                        Button(onClick = onStart, modifier = Modifier.weight(1f)) { Text("再開") }
                        OutlinedButton(onClick = onReset, modifier = Modifier.weight(1f)) { Text("リセット") }
                    }
                }
            }

            if (laps.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(Modifier.height(4.dp))
                val fastestLapMillis = laps.minOf { it.lapMillis }
                val slowestLapMillis = laps.maxOf { it.lapMillis }
                LazyColumn(modifier = Modifier.heightIn(max = 160.dp)) {
                    items(laps.reversed()) { lap ->
                        LapListItem(lap = lap, isFastest = laps.size >= 2 && lap.lapMillis == fastestLapMillis, isSlowest = laps.size >= 2 && lap.lapMillis == slowestLapMillis)
                    }
                }
            }
        }
    }

    if (showLabelDialog) {
        LabelInputDialog(
            initialLabel = stopwatch.label,
            onDismiss = { showLabelDialog = false },
            onConfirm = { label ->
                onLabelSet(label)
                showLabelDialog = false
            }
        )
    }
}

fun formatStopwatchMillis(millis: Long): String {
    val minutes = millis / 60000
    val seconds = (millis % 60000) / 1000
    val centis = (millis % 1000) / 10
    return "%02d:%02d.%02d".format(minutes, seconds, centis)
}
