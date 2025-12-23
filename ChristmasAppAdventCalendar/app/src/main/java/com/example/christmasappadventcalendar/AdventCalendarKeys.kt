package com.example.christmasappadventcalendar

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AdventCalendarKeys {

    fun dayOpenedKey(day: Int) = booleanPreferencesKey("day_${day}_opened")
    fun dayEmojiKey(day: Int) = stringPreferencesKey("day_${day}_emoji")
}

