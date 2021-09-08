package com.gmail.hamedvakhide.simpleroomtest.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.gmail.hamedvakhide.simpleroomtest.adapter.NoteAdapter
import javax.inject.Inject

// FragmentFactory is needed when we try to inject fragment constructor
class NoteFragmentFactory @Inject constructor(
    private val noteAdapter: NoteAdapter
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            NoteFragment::class.java.name -> NoteFragment(noteAdapter)
            AddEditFragment::class.java.name -> AddEditFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}