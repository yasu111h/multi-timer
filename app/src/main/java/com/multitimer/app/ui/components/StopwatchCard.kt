package com.multitimer.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.FiberManualRecord
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
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

    val accentColor = when (stopwatch.status) {
        StopwatchStatus.RUNNING -> MaterialTheme.colorScheme.primary
        StopwatchStatus.PAUSED -> MaterialTheme.colorScheme.secondary
        StopwatchStatus.IDLE -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(listOf(accentColor.copy(alpha = 0.6f), Color.Transparent)),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // ヘッダー
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stopwatch.label.ifEmpty { "— tap to name —" },
                        style = MaterialTheme.typography.labelLarge,
                        color = if (stopwatch.label.isEmpty())
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable { showLabelDialog = true }
                    )
                    IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                        Icon(
                            Icons.Default.Close, contentDescription = "削除",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // 時間表示
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = formatStopwatchMillis(displayMillis),
                        fontSize = 52.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Thin,
                        color = accentColor
                    )
                }

                Spacer(Modifier.height(16.dp))

                // アイコンボタン群
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // リセットボタン
                    IconButton(
                        onClick = onReset,
                        enabled = stopwatch.status != StopwatchStatus.IDLE,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (stopwatch.status != StopwatchStatus.IDLE)
                                    MaterialTheme.colorScheme.surfaceVariant
                                else Color.Transparent
                            )
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "リセット",
                            tint = if (stopwatch.status != StopwatchStatus.IDLE)
                                MaterialTheme.colorScheme.onSurfaceVariant
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(Modifier.width(20.dp))

                    // メインボタン（スタート/一時停止）
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                            .clickable {
                                when (stopwatch.status) {
                                    StopwatchStatus.IDLE, StopwatchStatus.PAUSED -> onStart()
                                    StopwatchStatus.RUNNING -> onPause()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            if (stopwatch.status == StopwatchStatus.RUNNING) Icons.Default.Pause
                            else Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(Modifier.width(20.dp))

                    // ラップボタン
                    IconButton(
                        onClick = onLap,
                        enabled = stopwatch.status == StopwatchStatus.RUNNING,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (stopwatch.status == StopwatchStatus.RUNNING)
                                    MaterialTheme.colorScheme.surfaceVariant
                                else Color.Transparent
                            )
                    ) {
                        Icon(
                            Icons.Outlined.FiberManualRecord,
                            contentDescription = "ラップ",
                            tint = if (stopwatch.status == StopwatchStatus.RUNNING)
                                MaterialTheme.colorScheme.onSurfaceVariant
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // ラップリスト
                if (laps.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(4.dp))
                    val fastestLapMillis = laps.minOf { it.lapMillis }
                    val slowestLapMillis = laps.maxOf { it.lapMillis }
                    LazyColumn(modifier = Modifier.heightIn(max = 160.dp)) {
                        items(laps.reversed()) { lap ->
                            LapListItem(
                                lap = lap,
                                isFastest = laps.size >= 2 && lap.lapMillis == fastestLapMillis,
                                isSlowest = laps.size >= 2 && lap.lapMillis == slowestLapMillis
                            )
                        }
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
