package com.antont.notes.db.repository

import com.antont.notes.db.dao.NoteDao
import com.antont.notes.db.entity.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getAll()

    fun insert(note: Note) {
        noteDao.insert(note)
    }

    fun delete(noteId: Int){
        noteDao.deleteByNoteId(noteId)
    }
}