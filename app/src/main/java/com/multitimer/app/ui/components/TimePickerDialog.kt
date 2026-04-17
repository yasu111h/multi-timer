package com.multitimer.app.ui.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimePickerDialog(
    initialMillis: Long,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    val initialSeconds = initialMillis / 1000
    val initialHours = (initialSeconds / 3600).toInt().coerceIn(0, 99)
    val initialMinutes = ((initialSeconds % 3600) / 60).toInt().coerceIn(0, 59)
    val initialSecs = (initialSeconds % 60).toInt().coerceIn(0, 59)

    var selectedHour by remember { mutableIntStateOf(initialHours) }
    var selectedMinute by remember { mutableIntStateOf(initialMinutes) }
    var selectedSecond by remember { mutableIntStateOf(initialSecs) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        title = {
            Text(
                "SET TIME",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Light,
                letterSpacing = 4.sp,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberWheelPicker(
                    range = 0..99,
                    initial = selectedHour,
                    modifier = Modifier.weight(1f)
                ) { selectedHour = it }

                Text(
                    "h",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp
                )
                Spacer(Modifier.width(4.dp))

                NumberWheelPicker(
                    range = 0..59,
                    initial = selectedMinute,
                    modifier = Modifier.weight(1f)
                ) { selectedMinute = it }

                Text(
                    "m",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp
                )
                Spacer(Modifier.width(4.dp))

                NumberWheelPicker(
                    range = 0..59,
                    initial = selectedSecond,
                    modifier = Modifier.weight(1f)
                ) { selectedSecond = it }

                Text(
                    "s",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp
                )
            }
        },
        confirmButton = {
            val totalMillis = (selectedHour * 3600L + selectedMinute * 60L + selectedSecond) * 1000L
            TextButton(
                onClick = { if (totalMillis > 0) onConfirm(totalMillis) },
                enabled = totalMillis > 0
            ) {
                Text(
                    "SET",
                    fontFamily = FontFamily.Monospace,
                    color = if (totalMillis > 0) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL", fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}

@Composable
fun NumberWheelPicker(
    range: IntRange,
    initial: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit
) {
    val itemCount = range.last - range.first + 1
    val visibleItems = 5
    val itemHeightDp = 44.dp

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (initial - range.first).coerceIn(0, itemCount - 1)
    )

    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val selectedIndex by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }

    LaunchedEffect(selectedIndex) {
        onValueChange(range.first + selectedIndex.coerceIn(0, itemCount - 1))
    }

    Box(modifier = modifier.height(itemHeightDp * visibleItems)) {
        LazyColumn(
            state = listState,
            flingBehavior = snapBehavior,
            contentPadding = PaddingValues(vertical = itemHeightDp * (visibleItems / 2)),
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemCount) { index ->
                val value = range.first + index
                val isSelected = index == selectedIndex
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeightDp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "%02d".format(value),
                        fontSize = if (isSelected) 28.sp else 20.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = if (isSelected) FontWeight.Normal else FontWeight.Thin,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.alpha(if (isSelected) 1f else 0.35f)
                    )
                }
            }
        }
    }
}
