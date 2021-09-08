package com.gmail.hamedvakhide.simpleroomtest.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.gmail.hamedvakhide.simpleroomtest.adapter.NoteAdapter
import com.gmail.hamedvakhide.simpleroomtest.repositories.FakeNotesRepositoryAndroidTest
import com.gmail.hamedvakhide.simpleroomtest.viewmodel.NoteViewModel
import javax.inject.Inject

// FragmentFactory is needed when we try to inject fragment constructor
class TestNoteFragmentFactory @Inject constructor(
    private val noteAdapter: NoteAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            NoteFragment::class.java.name -> NoteFragment(
                noteAdapter,
                NoteViewModel(FakeNotesRepositoryAndroidTest())
            )
            AddEditFragment::class.java.name -> AddEditFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}