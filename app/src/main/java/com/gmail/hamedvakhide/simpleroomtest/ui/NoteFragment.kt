package com.gmail.hamedvakhide.simpleroomtest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hamedvakhide.simpleroomtest.R
import com.gmail.hamedvakhide.simpleroomtest.adapter.NoteAdapter
import com.gmail.hamedvakhide.simpleroomtest.databinding.FragmentNoteBinding
import com.gmail.hamedvakhide.simpleroomtest.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment @Inject constructor(
    private val noteAdapter: NoteAdapter,
    var viewModel: NoteViewModel? = null
) : Fragment(R.layout.fragment_note) {

    private var _binding: FragmentNoteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNoteBinding.bind(view)
        _binding = binding

        viewModel = viewModel ?: ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.fabNote.setOnClickListener {
            findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToAddEditFragment())
        }

        viewModel?.notesList?.observe(viewLifecycleOwner,{
            noteAdapter.submitList(it)
        })

        // recycler view setup
        binding.rvNoteFragment.apply {
            this.adapter = noteAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

    // swipe to delete recycler view item
    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0,LEFT or RIGHT
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val note = noteAdapter.currentList[position]
            viewModel?.deleteNote(note)
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}