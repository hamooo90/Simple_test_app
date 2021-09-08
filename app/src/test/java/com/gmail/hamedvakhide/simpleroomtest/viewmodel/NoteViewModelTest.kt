package com.gmail.hamedvakhide.simpleroomtest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gmail.hamedvakhide.simpleroomtest.MainCoroutineRule
import com.gmail.hamedvakhide.simpleroomtest.data.Note
import com.gmail.hamedvakhide.simpleroomtest.getOrAwaitValueTest
import com.gmail.hamedvakhide.simpleroomtest.repositories.FakeNotesRepository
import com.gmail.hamedvakhide.simpleroomtest.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class NoteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /// rule to run coroutine on unit test
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup(){
        viewModel = NoteViewModel(FakeNotesRepository())
    }

    @Test
    fun `insert new note with empty fields return error`(){
        viewModel.insertNote(" ","")
        val status = viewModel.insertNoteStatus.getOrAwaitValueTest()
        assertThat(status.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert valid note return successful`(){
        viewModel.insertNote("test", "test")
        val status = viewModel.insertNoteStatus.getOrAwaitValueTest()
        assertThat(status.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `delete note is successful`(){
        val note = Note("test","test",1)
        viewModel.insertNoteIntoDb(note)
        viewModel.deleteNote(note)

        val list = viewModel.notesList.getOrAwaitValueTest()
        assertThat(list).isEmpty()

    }
}