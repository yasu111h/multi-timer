package com.teamhappslab.tick.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BulkActionBar(
    hasIdle: Boolean,
    hasRunning: Boolean,
    hasPaused: Boolean,
    onStartAll: () -> Unit,
    onPauseAll: () -> Unit,
    onResetAll: () -> Unit
) {
    Surface(tonalElevation = 4.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onStartAll,
                enabled = hasIdle || hasPaused,
                modifier = Modifier.weight(1f)
            ) { Text("全スタート", maxLines = 1) }
            OutlinedButton(
                onClick = onPauseAll,
                enabled = hasRunning,
                modifier = Modifier.weight(1f)
            ) { Text("全停止", maxLines = 1) }
            OutlinedButton(
                onClick = onResetAll,
                enabled = hasRunning || hasPaused,
                modifier = Modifier.weight(1f)
            ) { Text("全リセット", maxLines = 1) }
        }
    }
}
