package com.gmail.hamedvakhide.simpleroomtest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.gmail.hamedvakhide.simpleroomtest.R
import com.gmail.hamedvakhide.simpleroomtest.adapter.NoteAdapter
import com.gmail.hamedvakhide.simpleroomtest.data.Note
import com.gmail.hamedvakhide.simpleroomtest.getOrAwaitValue
import com.gmail.hamedvakhide.simpleroomtest.launchFragmentInHiltContainer
import com.gmail.hamedvakhide.simpleroomtest.repositories.FakeNotesRepositoryAndroidTest
import com.gmail.hamedvakhide.simpleroomtest.viewmodel.NoteViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class NoteFragmentTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testFragmentFactory: TestNoteFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickAddButton_navigateToAddFragment(){

        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<NoteFragment>(
            fragmentFactory = testFragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }
        onView(withId(R.id.fab_note)).perform(click())
        verify(navController).navigate(NoteFragmentDirections.actionNoteFragmentToAddEditFragment())
    }



    @Test
    fun clickOnNoteItemInRecyclerView_navigateToEditFragment(){
        val note = Note("test1","content test",1)
        var testViewModel: NoteViewModel ?= null
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<NoteFragment>(
            fragmentFactory = testFragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)

            testViewModel = viewModel
            viewModel?.insertNoteIntoDb(note)
        }
        onView(withId(R.id.rv_note_fragment)).perform(RecyclerViewActions
            .actionOnItemAtPosition<NoteAdapter.NoteViewHolder>(
                0,ViewActions.click()
            ))
        verify(navController).navigate(NoteFragmentDirections.actionNoteFragmentToAddEditFragment(Note("test1","content test",1)))
    }

    @Test
    fun swipeOnNoteItemInRecyclerView_DeleteNoteFromDb(){
        val note = Note("test1","content test",1)
        var testViewModel = NoteViewModel(FakeNotesRepositoryAndroidTest())
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<NoteFragment>(
            fragmentFactory = testFragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)

            testViewModel = viewModel!!
            viewModel?.insertNoteIntoDb(note)
        }
        onView(withId(R.id.rv_note_fragment)).perform(RecyclerViewActions
            .actionOnItemAtPosition<NoteAdapter.NoteViewHolder>(
                0,ViewActions.swipeLeft()
            ))
        val list = testViewModel.notesList.getOrAwaitValue()
        assertThat(list).isEmpty()
    }


}