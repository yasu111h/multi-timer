package com.teamhappslab.tick.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamhappslab.tick.data.db.entity.TimerEntity
import com.teamhappslab.tick.data.db.entity.TimerStatus

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
    var displayMillis by remember(timer.id, timer.status, timer.remainingMillis) { mutableLongStateOf(timer.remainingMillis) }

    LaunchedEffect(timer.id, timer.status, timer.startedAt, timer.remainingMillis) {
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

    val progress = if (timer.totalMillis > 0) displayMillis.toFloat() / timer.totalMillis else 0f

    val accentColor = when (timer.status) {
        TimerStatus.RUNNING -> MaterialTheme.colorScheme.primary
        TimerStatus.PAUSED -> MaterialTheme.colorScheme.secondary
        TimerStatus.FINISHED -> MaterialTheme.colorScheme.error
        TimerStatus.IDLE -> MaterialTheme.colorScheme.onSurfaceVariant
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
                    brush = Brush.horizontalGradient(listOf(accentColor, accentColor.copy(alpha = 0.2f))),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // ヘッダー行
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = timer.label.ifEmpty { "— tap to name —" },
                        style = MaterialTheme.typography.labelLarge,
                        color = if (timer.label.isEmpty())
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable { showLabelDialog = true }
                    )
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "削除",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // 時間表示
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = formatMillis(displayMillis),
                        fontSize = 56.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Thin,
                        color = accentColor,
                        modifier = Modifier.clickable(enabled = timer.status == TimerStatus.IDLE) {
                            showTimePicker = true
                        }
                    )
                }

                // プログレスバー
                if (timer.status != TimerStatus.IDLE) {
                    Spacer(Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .clip(RoundedCornerShape(1.dp)),
                        color = accentColor,
                        trackColor = accentColor.copy(alpha = 0.15f)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // コントロールボタン
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // リセットボタン
                    IconButton(
                        onClick = onReset,
                        enabled = timer.status != TimerStatus.IDLE,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (timer.status != TimerStatus.IDLE)
                                    Color(0xFF2A2A2A)
                                else Color.Transparent
                            )
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "リセット",
                            tint = if (timer.status != TimerStatus.IDLE)
                                MaterialTheme.colorScheme.onSurfaceVariant
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.25f),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(Modifier.width(24.dp))

                    // メインボタン（スタート/一時停止/再開）
                    val mainAction: () -> Unit = when (timer.status) {
                        TimerStatus.IDLE -> onStart
                        TimerStatus.RUNNING -> onPause
                        TimerStatus.PAUSED -> onResume
                        TimerStatus.FINISHED -> onReset
                    }
                    val mainIcon = when (timer.status) {
                        TimerStatus.RUNNING -> Icons.Default.Pause
                        else -> Icons.Default.PlayArrow
                    }
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                            .clickable { mainAction() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            mainIcon,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(Modifier.width(24.dp))

                    // タイマー設定ボタン（IDLEのみ）
                    IconButton(
                        onClick = { showTimePicker = true },
                        enabled = timer.status == TimerStatus.IDLE,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (timer.status == TimerStatus.IDLE)
                                    Color(0xFF2A2A2A)
                                else Color.Transparent
                            )
                    ) {
                        Text(
                            text = "SET",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = if (timer.status == TimerStatus.IDLE)
                                MaterialTheme.colorScheme.onSurfaceVariant
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                        )
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
