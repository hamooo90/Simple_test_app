package com.gmail.hamedvakhide.simpleroomtest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.hamedvakhide.simpleroomtest.data.Note

// A fake repository to be used when we test ViewModel
// This class exactly replicate NoteRepository tasks but on a list not Room database
class FakeNotesRepository: NotesRepository {
    private val notesList = mutableListOf<Note>()

    private val observableNotesList = MutableLiveData<List<Note>>(notesList)

    override suspend fun insertNote(note: Note) {
        notesList.add(note)
        observableNotesList.postValue(notesList)
    }

    override suspend fun deleteNote(note: Note) {
        notesList.remove(note)
        observableNotesList.postValue(notesList)
    }

    override fun observeAllNotes(): LiveData<List<Note>> {
        return observableNotesList
    }
}