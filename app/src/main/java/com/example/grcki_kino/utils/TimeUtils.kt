package com.example.grcki_kino.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.grcki_kino.data.RoundDataClass
import kotlinx.coroutines.delay
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


object TimeUtils {

    private fun calculateRemainingTimeInSeconds(targetTime: Long): Int {
        val currentTime = java.util.Date().time
        val difference = targetTime - currentTime
        return if (difference > 0) {
            (difference / 1000).toInt()
        } else {
            0
        }
    }

    @Composable
    fun Stopwatch(
        roundDataClass: RoundDataClass,
        onTimeUpdate: (String) -> Unit,
        onTimeUp: () -> Unit
    ) {
        val targetTime = roundDataClass.drawTime
        var remainingTimeInSeconds by remember {
            mutableIntStateOf(calculateRemainingTimeInSeconds(targetTime))
        }

        // Update the countdown timer
        LaunchedEffect(roundDataClass.drawId) {
            while (remainingTimeInSeconds > 0) {
                delay(1000)
                remainingTimeInSeconds = calculateRemainingTimeInSeconds(targetTime)

                val minutes = remainingTimeInSeconds / 60
                val seconds = remainingTimeInSeconds % 60
                val timeDisplay = String.format("%02d:%02d", minutes, seconds)
                onTimeUpdate(timeDisplay)
            }

            delay(1500)
            onTimeUp()
        }
    }

    fun parseTimestampToTime(timestamp: Long): String {
        val date = Date(timestamp)

        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()

        return formatter.format(date)
    }
}