package com.antont.notes

import android.app.Application
import androidx.room.Room
import com.antont.notes.db.AppDatabase
import com.antont.notes.db.repository.NoteRepository

class NotesApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            this, AppDatabase::class.java, "notes_database"
        ).build()
    }
    val repository by lazy { NoteRepository(database.noteDao()) }
}