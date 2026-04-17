package com.multitimer.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.multitimer.app.data.db.entity.LapEntity

@Composable
fun LapListItem(lap: LapEntity, isFastest: Boolean, isSlowest: Boolean) {
    val color = when {
        isFastest -> Color(0xFF4CAF50)
        isSlowest -> Color(0xFFF44336)
        else -> MaterialTheme.colorScheme.onSurface
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ラップ番号（固定幅）
        Text(
            text = "Lap ${lap.lapNumber}",
            color = color,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(56.dp)
        )
        // トロフィーアイコン（固定幅・レイアウトが崩れないよう分離）
        Text(
            text = if (isFastest) "\uD83C\uDFC6" else "",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(20.dp)
        )
        // ラップタイム（中央寄せ）
        Text(
            text = formatStopwatchMillis(lap.lapMillis),
            color = color,
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        // 累計タイム（右寄せ）
        Text(
            text = formatStopwatchMillis(lap.cumulativeMillis),
            color = color,
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End,
            modifier = Modifier.width(80.dp)
        )
    }
}
