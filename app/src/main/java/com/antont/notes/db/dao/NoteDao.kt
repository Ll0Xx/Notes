package com.antont.notes.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.antont.notes.db.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>
    @Insert
    fun save(note: Note)

    @Query("DELETE FROM note WHERE uid = :noteId")
    fun deleteByNoteId(noteId: Int)
}