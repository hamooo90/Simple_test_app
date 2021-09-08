package com.gmail.hamedvakhide.simpleroomtest.repositories

import androidx.lifecycle.LiveData
import com.gmail.hamedvakhide.simpleroomtest.data.Note
import com.gmail.hamedvakhide.simpleroomtest.data.NoteDao
import javax.inject.Inject

class DefaultNoteRepository @Inject constructor(
    private val noteDao: NoteDao
): NotesRepository {
    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }

    override fun observeAllNotes(): LiveData<List<Note>> {
        return noteDao.observeAllNotes()
    }
}