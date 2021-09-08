package com.gmail.hamedvakhide.simpleroomtest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.gmail.hamedvakhide.simpleroomtest.R
import com.gmail.hamedvakhide.simpleroomtest.data.Note
import com.gmail.hamedvakhide.simpleroomtest.getOrAwaitValue
import com.gmail.hamedvakhide.simpleroomtest.launchFragmentInHiltContainer
import com.gmail.hamedvakhide.simpleroomtest.repositories.FakeNotesRepositoryAndroidTest
import com.gmail.hamedvakhide.simpleroomtest.util.Status
import com.gmail.hamedvakhide.simpleroomtest.viewmodel.NoteViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class AddEditFragmentTest{

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

//    @Test
//    fun pressBackButton_popBackStack() {
//        val navController = Mockito.mock(NavController::class.java)
//        launchFragmentInHiltContainer<AddEditFragment>(
//            fragmentArgs = bundleOf("noteArg" to null)
//        ) {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        pressBack()
//        verify(navController).popBackStack()
//    }

    @Test
    fun clickInsertToDb_returnSuccessful(){
        val testViewModel = NoteViewModel(FakeNotesRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddEditFragment>(
            fragmentArgs = bundleOf("noteArg" to null)
        ) {
            viewModel = testViewModel
        }
        onView(withId(R.id.et_title_add_edit_fragment)).perform(replaceText("title1"))
        onView(withId(R.id.et_content_add_edit_fragment)).perform(replaceText("title1"))
        onView(withId(R.id.fab_add_edit)).perform(click())
        assertThat(testViewModel.notesList.getOrAwaitValue())
            .contains(Note(
                "title1","title1"
            ))
    }

    @Test
    fun clickInsertToDbWithEmptyField_returnError(){
        val testViewModel = NoteViewModel(FakeNotesRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddEditFragment>(
            fragmentArgs = bundleOf("noteArg" to null)
        ) {
            viewModel = testViewModel
        }
        onView(withId(R.id.et_title_add_edit_fragment)).perform(replaceText("title2"))
        onView(withId(R.id.et_content_add_edit_fragment)).perform(replaceText(""))
        onView(withId(R.id.fab_add_edit)).perform(click())
        val status = testViewModel.insertNoteStatus.getOrAwaitValue().getContentIfNotHandled()?.status
        assertThat(status).isEqualTo(Status.ERROR)
    }

    @Test
    fun clickToUpdateNoteInDbWithSameId_returnSuccess(){
        val testViewModel = NoteViewModel(FakeNotesRepositoryAndroidTest())
        val note = Note("title","content",1)
        testViewModel.insertNoteIntoDb(note)
        launchFragmentInHiltContainer<AddEditFragment>(
            fragmentArgs = bundleOf("noteArg" to Note("title","content",1))
        ) {
            viewModel = testViewModel

        }
        onView(withId(R.id.et_title_add_edit_fragment)).perform(replaceText("edit title"))
        onView(withId(R.id.fab_add_edit)).perform(click())

        val list = testViewModel.notesList.getOrAwaitValue()
        assertThat(list).contains(Note("edit title","content",1))
    }
}