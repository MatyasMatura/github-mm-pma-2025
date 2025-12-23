package com.example.christmasappadventcalendar

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AdventCalendarRepository(private val context: Context) {

    private val dataStore = context.adventCalendarDataStore

    // Mark a day as opened with its emoji
    suspend fun markDayAsOpened(day: Int, emoji: String) {
        dataStore.edit { prefs ->
            prefs[AdventCalendarKeys.dayOpenedKey(day)] = true
            prefs[AdventCalendarKeys.dayEmojiKey(day)] = emoji
        }
    }

    // Check if a day is opened
    fun isDayOpened(day: Int): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[AdventCalendarKeys.dayOpenedKey(day)] ?: false
        }
    }

    // Get the emoji for a specific day
    fun getDayEmoji(day: Int): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[AdventCalendarKeys.dayEmojiKey(day)]
        }
    }

    // Get all opened days
    fun getAllOpenedDays(): Flow<Set<Int>> {
        return dataStore.data.map { prefs ->
            (1..24).filter { day ->
                prefs[AdventCalendarKeys.dayOpenedKey(day)] ?: false
            }.toSet()
        }
    }
}

