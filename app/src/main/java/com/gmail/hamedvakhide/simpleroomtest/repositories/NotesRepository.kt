package com.gmail.hamedvakhide.simpleroomtest.repositories

import androidx.lifecycle.LiveData
import com.gmail.hamedvakhide.simpleroomtest.data.Note

interface NotesRepository {

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun observeAllNotes(): LiveData<List<Note>>
}