package com.gmail.hamedvakhide.simpleroomtest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.hamedvakhide.simpleroomtest.data.Note
import com.gmail.hamedvakhide.simpleroomtest.repositories.NotesRepository
import com.gmail.hamedvakhide.simpleroomtest.util.Event
import com.gmail.hamedvakhide.simpleroomtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _insertNoteStatus = MutableLiveData<Event<Resource<Note>>>()
    val insertNoteStatus: LiveData<Event<Resource<Note>>>
        get() = _insertNoteStatus

    val notesList = repository.observeAllNotes()

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun insertNoteIntoDb(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun insertNote(title: String, content: String){
        if(title.trim().isEmpty() || content.trim().isEmpty()){
            _insertNoteStatus.postValue(Event(Resource.Error("Fields can't be empty!")))
            return
        }

        val note = Note(title.trim(),content.trim())
        insertNoteIntoDb(note)
        _insertNoteStatus.postValue(Event(Resource.Success(note)))
    }
    fun updateNote(title: String, content: String, id: Int){
        if(title.trim().isEmpty() || content.trim().isEmpty()){
            _insertNoteStatus.postValue(Event(Resource.Error("Fields can't be empty!")))
            return
        }

        val note = Note(title.trim(),content.trim(),id)
        insertNoteIntoDb(note)
        _insertNoteStatus.postValue(Event(Resource.Success(note)))
    }

}