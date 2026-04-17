package com.multitimer.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TimePickerDialog(
    initialMillis: Long,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    val initialSeconds = initialMillis / 1000
    var hours by remember { mutableStateOf((initialSeconds / 3600).toString()) }
    var minutes by remember { mutableStateOf(((initialSeconds % 3600) / 60).toString()) }
    var seconds by remember { mutableStateOf((initialSeconds % 60).toString()) }

    val quickOptions = listOf(1 to "1分", 3 to "3分", 5 to "5分", 10 to "10分", 30 to "30分")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("時間を設定") },
        text = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = hours, onValueChange = { if (it.length <= 2) hours = it.filter { c -> c.isDigit() } },
                        label = { Text("時") }, modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Text(":")
                    OutlinedTextField(
                        value = minutes, onValueChange = { if (it.length <= 2) minutes = it.filter { c -> c.isDigit() } },
                        label = { Text("分") }, modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Text(":")
                    OutlinedTextField(
                        value = seconds, onValueChange = { if (it.length <= 2) seconds = it.filter { c -> c.isDigit() } },
                        label = { Text("秒") }, modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    quickOptions.forEach { (min, label) ->
                        FilterChip(
                            selected = false,
                            onClick = { minutes = min.toString(); hours = "0"; seconds = "0" },
                            label = { Text(label) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            val totalMillis = ((hours.toLongOrNull() ?: 0) * 3600 +
                    (minutes.toLongOrNull() ?: 0) * 60 +
                    (seconds.toLongOrNull() ?: 0)) * 1000
            TextButton(onClick = { if (totalMillis > 0) onConfirm(totalMillis) }, enabled = totalMillis > 0) {
                Text("設定")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("キャンセル") } }
    )
}
