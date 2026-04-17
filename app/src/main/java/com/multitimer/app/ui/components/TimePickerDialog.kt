package com.multitimer.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

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

    // クイックボタンで合計秒を加算し各フィールドに分配
    fun addSeconds(addSecs: Int) {
        val total = (selectedHour * 3600 + selectedMinute * 60 + selectedSecond + addSecs)
            .coerceIn(0, 99 * 3600 + 59 * 60 + 59)
        selectedHour = total / 3600
        selectedMinute = (total % 3600) / 60
        selectedSecond = total % 60
    }

    Dialog(onDismissRequest = onDismiss) {
        val borderColor = MaterialTheme.colorScheme.primary
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(borderColor, borderColor.copy(alpha = 0.2f))
                ),
                shape = RoundedCornerShape(24.dp)
            )
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
                Text(
                    "SET TIME",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 4.sp,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(16.dp))

                // ホイールピッカー
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NumberWheelPicker(
                        range = 0..99,
                        initial = initialHours,
                        scrollToValue = selectedHour,
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
                        initial = initialMinutes,
                        scrollToValue = selectedMinute,
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
                        initial = initialSecs,
                        scrollToValue = selectedSecond,
                        modifier = Modifier.weight(1f)
                    ) { selectedSecond = it }

                    Text(
                        "s",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 18.sp
                    )
                }

                Spacer(Modifier.height(12.dp))

                // クイック追加ボタン（+は便利、-は不要：ホイールで任意値設定可能）
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(
                        60 to "+1m",
                        5 * 60 to "+5m",
                        10 * 60 to "+10m",
                        3600 to "+1h"
                    ).forEach { (secs, label) ->
                        OutlinedButton(
                            onClick = { addSeconds(secs) },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                label,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                val totalMillis = (selectedHour * 3600L + selectedMinute * 60L + selectedSecond) * 1000L
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            "CANCEL",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(Modifier.width(8.dp))
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
                }
            }
        }
    }
}

@Composable
fun NumberWheelPicker(
    range: IntRange,
    initial: Int,
    scrollToValue: Int = initial,
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

    // 外部からのスクロール制御（クイックボタン・初期位置の確定）
    // scrollOffset = 0 にすることでセンター位置に正確に揃える
    LaunchedEffect(scrollToValue) {
        val targetIndex = (scrollToValue - range.first).coerceIn(0, itemCount - 1)
        listState.animateScrollToItem(targetIndex, scrollOffset = 0)
    }

    val selectedIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }

    LaunchedEffect(selectedIndex) {
        onValueChange(range.first + selectedIndex.coerceIn(0, itemCount - 1))
    }

    Box(modifier = modifier.height(itemHeightDp * visibleItems)) {
        // 選択中行のハイライト背景
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(itemHeightDp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                )
        )

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
