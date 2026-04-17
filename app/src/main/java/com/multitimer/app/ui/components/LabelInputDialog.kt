package com.multitimer.app.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun LabelInputDialog(
    initialLabel: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var label by remember { mutableStateOf(initialLabel) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("ラベルを設定") },
        text = {
            OutlinedTextField(
                value = label,
                onValueChange = { if (it.length <= 20) label = it },
                label = { Text("ラベル名（最大20文字）") },
                supportingText = { Text("${label.length}/20") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(label) }) { Text("保存") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("キャンセル") } }
    )
}
