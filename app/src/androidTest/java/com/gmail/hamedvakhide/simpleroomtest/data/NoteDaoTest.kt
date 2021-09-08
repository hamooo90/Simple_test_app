package com.gmail.hamedvakhide.simpleroomtest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gmail.hamedvakhide.simpleroomtest.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class NoteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: NotesDatabase

    private lateinit var dao: NoteDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.noteDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertNoteIntoDb_isSuccessful() = runBlockingTest {
        val note = Note("test","it's a test content ",1)
        dao.insertNote(note)

        val list = dao.observeAllNotes().getOrAwaitValue()
        assertThat(list).contains(note)
    }

    @Test
    fun deleteNoteFromDb_isSuccessful() = runBlockingTest {
        val note = Note("test","it's a test content ",1)
        dao.insertNote(note)
        dao.delete(note)

        val list = dao.observeAllNotes().getOrAwaitValue()
        assertThat(list).isEmpty()
    }
}