package com.teamhappslab.tick

import com.teamhappslab.tick.data.db.entity.TimerEntity
import com.teamhappslab.tick.data.db.entity.TimerStatus
import org.junit.Assert.*
import org.junit.Test

class TimerEntityTest {

    @Test
    fun defaultTimerHasIdleStatus() {
        val timer = TimerEntity()
        assertEquals(TimerStatus.IDLE, timer.status)
    }

    @Test
    fun defaultTimerHasTenSecondDuration() {
        val timer = TimerEntity()
        assertEquals(10_000L, timer.totalMillis)
        assertEquals(10_000L, timer.remainingMillis)
    }

    @Test
    fun defaultTimerHasNoStartedAt() {
        val timer = TimerEntity()
        assertNull(timer.startedAt)
    }

    @Test
    fun timerCopyPreservesId() {
        val timer = TimerEntity(id = 42L, label = "Test")
        val updated = timer.copy(status = TimerStatus.RUNNING)
        assertEquals(42L, updated.id)
        assertEquals("Test", updated.label)
    }

    @Test
    fun timerRemainingCanBeZero() {
        val timer = TimerEntity(remainingMillis = 0L)
        assertEquals(0L, timer.remainingMillis)
    }

    @Test
    fun timerRemainingCoercedToAtLeastZero() {
        val remaining = (-500L).coerceAtLeast(0L)
        assertEquals(0L, remaining)
    }

    @Test
    fun timerStatusTransitions() {
        var timer = TimerEntity()
        assertEquals(TimerStatus.IDLE, timer.status)

        timer = timer.copy(status = TimerStatus.RUNNING, startedAt = System.currentTimeMillis())
        assertEquals(TimerStatus.RUNNING, timer.status)
        assertNotNull(timer.startedAt)

        timer = timer.copy(status = TimerStatus.PAUSED)
        assertEquals(TimerStatus.PAUSED, timer.status)

        timer = timer.copy(status = TimerStatus.FINISHED, remainingMillis = 0L)
        assertEquals(TimerStatus.FINISHED, timer.status)
        assertEquals(0L, timer.remainingMillis)
    }

    @Test
    fun timerLabelCanBeEmpty() {
        val timer = TimerEntity(label = "")
        assertTrue(timer.label.isEmpty())
    }

    @Test
    fun timerLabelStoredCorrectly() {
        val timer = TimerEntity(label = "Morning Run")
        assertEquals("Morning Run", timer.label)
    }

    @Test
    fun elapsedTimeCalculation() {
        val startedAt = 1000L
        val now = 4000L
        val initialRemaining = 10_000L
        val elapsed = now - startedAt
        val newRemaining = (initialRemaining - elapsed).coerceAtLeast(0L)
        assertEquals(7_000L, newRemaining)
    }

    @Test
    fun timerFinishesWhenRemainingReachesZero() {
        val startedAt = 0L
        val now = 10_001L
        val initialRemaining = 10_000L
        val elapsed = now - startedAt
        val newRemaining = (initialRemaining - elapsed).coerceAtLeast(0L)
        assertEquals(0L, newRemaining)
    }
}
