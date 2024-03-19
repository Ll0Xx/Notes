package com.antont.notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antont.notes.db.dao.NoteDao
import com.antont.notes.db.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}