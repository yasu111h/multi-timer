package com.multitimer.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multitimer.app.data.db.entity.TimerEntity
import com.multitimer.app.data.db.entity.TimerStatus

@Composable
fun TimerCard(
    timer: TimerEntity,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onReset: () -> Unit,
    onDelete: () -> Unit,
    onTimeSet: (Long) -> Unit,
    onLabelSet: (String) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var showLabelDialog by remember { mutableStateOf(false) }

    // RUNNING中は実際の残り時間を画面でリアルタイム更新するためローカルstateを使う
    var displayMillis by remember(timer.id, timer.status) { mutableLongStateOf(timer.remainingMillis) }

    LaunchedEffect(timer.id, timer.status, timer.startedAt) {
        if (timer.status == TimerStatus.RUNNING && timer.startedAt != null) {
            while (true) {
                val elapsed = System.currentTimeMillis() - timer.startedAt
                displayMillis = (timer.remainingMillis - elapsed).coerceAtLeast(0)
                if (displayMillis <= 0) break
                kotlinx.coroutines.delay(100)
            }
        } else {
            displayMillis = timer.remainingMillis
        }
    }

    val cardColor = when (timer.status) {
        TimerStatus.RUNNING -> MaterialTheme.colorScheme.primaryContainer
        TimerStatus.PAUSED -> MaterialTheme.colorScheme.secondaryContainer
        TimerStatus.FINISHED -> MaterialTheme.colorScheme.errorContainer
        TimerStatus.IDLE -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = timer.label.ifEmpty { "ラベルなし" },
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.clickable { showLabelDialog = true }
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "削除", modifier = Modifier.size(16.dp))
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = formatMillis(displayMillis),
                fontSize = 48.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(enabled = timer.status == TimerStatus.IDLE) { showTimePicker = true }
            )

            if (timer.status == TimerStatus.RUNNING) {
                LinearProgressIndicator(
                    progress = { if (timer.totalMillis > 0) displayMillis.toFloat() / timer.totalMillis else 0f },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                when (timer.status) {
                    TimerStatus.IDLE -> {
                        Button(onClick = onStart, modifier = Modifier.weight(1f)) { Text("スタート") }
                    }
                    TimerStatus.RUNNING -> {
                        Button(onClick = onPause, modifier = Modifier.weight(1f)) { Text("一時停止") }
                        OutlinedButton(onClick = onReset, modifier = Modifier.weight(1f)) { Text("リセット") }
                    }
                    TimerStatus.PAUSED -> {
                        Button(onClick = onResume, modifier = Modifier.weight(1f)) { Text("再開") }
                        OutlinedButton(onClick = onReset, modifier = Modifier.weight(1f)) { Text("リセット") }
                    }
                    TimerStatus.FINISHED -> {
                        Button(onClick = onReset, modifier = Modifier.weight(1f)) { Text("リセット") }
                    }
                }
            }
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            initialMillis = timer.totalMillis,
            onDismiss = { showTimePicker = false },
            onConfirm = { millis ->
                onTimeSet(millis)
                showTimePicker = false
            }
        )
    }

    if (showLabelDialog) {
        LabelInputDialog(
            initialLabel = timer.label,
            onDismiss = { showLabelDialog = false },
            onConfirm = { label ->
                onLabelSet(label)
                showLabelDialog = false
            }
        )
    }
}

fun formatMillis(millis: Long): String {
    val totalSeconds = millis / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0) {
        "%02d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%02d:%02d".format(minutes, seconds)
    }
}
