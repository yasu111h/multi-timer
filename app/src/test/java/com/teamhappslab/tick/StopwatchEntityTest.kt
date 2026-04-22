package com.teamhappslab.tick

import com.teamhappslab.tick.data.db.entity.StopwatchEntity
import com.teamhappslab.tick.data.db.entity.StopwatchStatus
import org.junit.Assert.*
import org.junit.Test

class StopwatchEntityTest {

    @Test
    fun defaultStopwatchHasIdleStatus() {
        val sw = StopwatchEntity()
        assertEquals(StopwatchStatus.IDLE, sw.status)
    }

    @Test
    fun defaultStopwatchHasZeroElapsed() {
        val sw = StopwatchEntity()
        assertEquals(0L, sw.elapsedMillis)
    }

    @Test
    fun defaultStopwatchHasNoStartedAt() {
        val sw = StopwatchEntity()
        assertNull(sw.startedAt)
    }

    @Test
    fun defaultStopwatchHasZeroLapCount() {
        val sw = StopwatchEntity()
        assertEquals(0, sw.lapCount)
    }

    @Test
    fun pauseAccumulatesElapsedTime() {
        val startedAt = 1000L
        val now = 4000L
        val sw = StopwatchEntity(
            status = StopwatchStatus.RUNNING,
            startedAt = startedAt,
            elapsedMillis = 0L
        )
        val elapsed = if (sw.startedAt != null) now - sw.startedAt else 0L
        val paused = sw.copy(
            status = StopwatchStatus.PAUSED,
            elapsedMillis = sw.elapsedMillis + elapsed,
            startedAt = null
        )
        assertEquals(StopwatchStatus.PAUSED, paused.status)
        assertEquals(3000L, paused.elapsedMillis)
        assertNull(paused.startedAt)
    }

    @Test
    fun resetClearsAllFields() {
        val sw = StopwatchEntity(
            status = StopwatchStatus.PAUSED,
            elapsedMillis = 5000L,
            lapCount = 3,
            currentLapStartMillis = 2000L
        )
        val reset = sw.copy(
            status = StopwatchStatus.IDLE,
            elapsedMillis = 0L,
            startedAt = null,
            pausedAt = null,
            currentLapStartMillis = 0L,
            lapCount = 0
        )
        assertEquals(StopwatchStatus.IDLE, reset.status)
        assertEquals(0L, reset.elapsedMillis)
        assertNull(reset.startedAt)
        assertEquals(0L, reset.currentLapStartMillis)
        assertEquals(0, reset.lapCount)
    }

    @Test
    fun lapMillisCalculation() {
        val elapsedMillis = 5000L
        val currentLapStart = 3000L
        val lapMillis = elapsedMillis - currentLapStart
        assertEquals(2000L, lapMillis)
    }

    @Test
    fun cumulativeElapsedWithStartedAt() {
        val sw = StopwatchEntity(
            status = StopwatchStatus.RUNNING,
            elapsedMillis = 2000L,
            startedAt = 1000L
        )
        val now = 4000L
        val total = sw.elapsedMillis + (now - (sw.startedAt ?: now))
        assertEquals(5000L, total)
    }
}
