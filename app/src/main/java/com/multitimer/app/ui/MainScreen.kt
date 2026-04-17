package com.multitimer.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.multitimer.app.R
import com.multitimer.app.ui.stopwatch.StopwatchScreen
import com.multitimer.app.ui.timer.TimerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("タイマー", "ストップウォッチ")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MultiTimer") },
                actions = {
                    IconButton(onClick = { /* TODO: 設定画面 */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "設定")
                    }
                }
            )
        }
    ) { paddingValues ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.padding(paddingValues)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedTab) {
                0 -> TimerScreen()
                1 -> StopwatchScreen()
            }
        }
    }
}
