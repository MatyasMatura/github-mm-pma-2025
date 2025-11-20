package com.example.cvikoapp11mynotehub.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 2,
    exportSchema = false
)

abstract class NoteHubDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}