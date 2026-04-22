package com.teamhappslab.tick

import org.junit.Assert.*
import org.junit.Test

/**
 * タイマー表示用の時間フォーマットロジックのテスト
 * アプリ内で使われる mm:ss / HH:mm:ss 形式の変換を検証する
 */
class TimeFormatTest {

    private fun formatMillis(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return if (hours > 0) {
            "%02d:%02d:%02d".format(hours, minutes, seconds)
        } else {
            "%02d:%02d".format(minutes, seconds)
        }
    }

    @Test
    fun zeroMillisFormatsAsZero() {
        assertEquals("00:00", formatMillis(0L))
    }

    @Test
    fun tenSecondsFormats() {
        assertEquals("00:10", formatMillis(10_000L))
    }

    @Test
    fun oneMinuteFormats() {
        assertEquals("01:00", formatMillis(60_000L))
    }

    @Test
    fun fiveMinutesFormats() {
        assertEquals("05:00", formatMillis(300_000L))
    }

    @Test
    fun ninetySecondsFormats() {
        assertEquals("01:30", formatMillis(90_000L))
    }

    @Test
    fun oneHourFormats() {
        assertEquals("01:00:00", formatMillis(3_600_000L))
    }

    @Test
    fun oneHourThirtyMinutesFormats() {
        assertEquals("01:30:00", formatMillis(5_400_000L))
    }

    @Test
    fun tenHoursFormats() {
        assertEquals("10:00:00", formatMillis(36_000_000L))
    }

    @Test
    fun partialSecondsIgnored() {
        // 1999ms は 1秒として扱う（切り捨て）
        assertEquals("00:01", formatMillis(1_999L))
    }

    @Test
    fun oneHourFiveMinutesThirtySecondsFormats() {
        val millis = (1 * 3600 + 5 * 60 + 30) * 1000L
        assertEquals("01:05:30", formatMillis(millis))
    }
}
