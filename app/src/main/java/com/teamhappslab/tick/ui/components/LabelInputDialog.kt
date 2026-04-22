package com.teamhappslab.tick.ui.components

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
        title = { Text("SET LABEL") },
        text = {
            OutlinedTextField(
                value = label,
                onValueChange = { if (it.length <= 20) label = it },
                label = { Text("Name (max 20 chars)") },
                supportingText = { Text("${label.length}/20") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(label) }) { Text("SAVE") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("CANCEL") } }
    )
}
