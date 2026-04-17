package com.multitimer.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ダークテーマカラー（OLED黒ベース・シアンアクセント）
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF),
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF003A40),
    onPrimaryContainer = Color(0xFF00E5FF),
    secondary = Color(0xFF00BFA5),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF003731),
    onSecondaryContainer = Color(0xFF00BFA5),
    tertiary = Color(0xFFFF6D00),
    background = Color(0xFF0A0A0A),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF121212),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = Color(0xFFB0BEC5),
    error = Color(0xFFFF5252),
    onError = Color(0xFF000000),
    errorContainer = Color(0xFF3A0000),
    onErrorContainer = Color(0xFFFF5252),
    outline = Color(0xFF2C2C2C),
)

@Composable
fun MultiTimerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
