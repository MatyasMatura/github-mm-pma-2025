package com.example.christmasappadventcalendar

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.adventCalendarDataStore by preferencesDataStore("advent_calendar")
