package com.teamhappslab.tick.ui.settings

import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamhappslab.tick.R
import com.teamhappslab.tick.data.repository.TimerSoundType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val vibrationEnabled by viewModel.vibrationEnabled.collectAsState()
    val soundEnabled by viewModel.soundEnabled.collectAsState()
    val keepScreenOn by viewModel.keepScreenOn.collectAsState()
    val soundType by viewModel.soundType.collectAsState()

    val context = LocalContext.current
    val activity = LocalActivity.current

    LaunchedEffect(keepScreenOn) {
        if (keepScreenOn) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "SETTINGS",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp,
                        letterSpacing = 8.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                windowInsets = WindowInsets(top = 24.dp)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // SOUND セクション
            item {
                SectionHeader(title = "SOUND")
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "終了音",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "Timer completion sound",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = soundEnabled,
                            onCheckedChange = { viewModel.setSoundEnabled(it) }
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
            if (soundEnabled) {
                item {
                    SoundTypeOption(
                        label = "今の音",
                        description = "Standard sound",
                        soundRes = R.raw.alarm_sound,
                        selected = soundType == TimerSoundType.DEFAULT,
                        onSelect = { viewModel.setSoundType(TimerSoundType.DEFAULT) }
                    )
                }
                item {
                    SoundTypeOption(
                        label = "けたたましい音",
                        description = "Loud alert sound",
                        soundRes = R.raw.alarm_sound_loud,
                        selected = soundType == TimerSoundType.LOUD,
                        onSelect = { viewModel.setSoundType(TimerSoundType.LOUD) }
                    )
                }
            }
            item { HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant) }

            // VIBRATION セクション
            item {
                SectionHeader(title = "VIBRATION")
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "バイブレーション",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "Vibrate on timer completion",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = vibrationEnabled,
                            onCheckedChange = { viewModel.setVibrationEnabled(it) }
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
            item { HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant) }

            // DISPLAY セクション
            item {
                SectionHeader(title = "DISPLAY")
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "画面を常時ON",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "Keep screen on while timers run",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = keepScreenOn,
                            onCheckedChange = { viewModel.setKeepScreenOn(it) }
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
            item { HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant) }

            // NOTIFICATIONS セクション
            item {
                SectionHeader(title = "NOTIFICATIONS")
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "通知設定を開く",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.clickable {
                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        }
                        context.startActivity(intent)
                    }
                )
            }
            item { HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant) }

            // ABOUT セクション
            item {
                SectionHeader(title = "ABOUT")
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "Version",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    trailingContent = {
                        Text(
                            text = "1.0.0",
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    }
}

@Composable
private fun SoundTypeOption(
    label: String,
    description: String,
    soundRes: Int,
    selected: Boolean,
    onSelect: () -> Unit
) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    DisposableEffect(soundRes) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    ListItem(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onSelect() },
        headlineContent = {
            Text(
                text = label,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        supportingContent = {
            Text(
                text = description,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
        },
        leadingContent = {
            RadioButton(selected = selected, onClick = onSelect)
        },
        trailingContent = {
            IconButton(onClick = {
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(context, soundRes)?.apply {
                    setOnCompletionListener { it.release() }
                    start()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "試聴",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 2.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}
