package com.gmail.hamedvakhide.simpleroomtest.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.hamedvakhide.simpleroomtest.R
import com.gmail.hamedvakhide.simpleroomtest.databinding.FragmentAddEditBinding
import com.gmail.hamedvakhide.simpleroomtest.util.Status
import com.gmail.hamedvakhide.simpleroomtest.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    lateinit var viewModel: NoteViewModel

    val args : AddEditFragmentArgs by navArgs()

    private var _binding: FragmentAddEditBinding? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddEditBinding.bind(view)
        _binding = binding

        val note = args.noteArg
        if(note !=null){
            binding.etTitleAddEditFragment.setText(note.title)
            binding.etContentAddEditFragment.setText(note.content)
        }

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.fabAddEdit.setOnClickListener {
            val title = binding.etTitleAddEditFragment.text.toString()
            val content = binding.etContentAddEditFragment.text.toString()

            if(note == null) {
                // insert new note
                viewModel.insertNote(title, content)
            } else {
                // update clicked note
                note.id?.let { it1 -> viewModel.updateNote(title,content, it1) }
            }
        }

        viewModel.insertNoteStatus.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(requireView(), result.message.toString(),Snackbar.LENGTH_LONG)
                            .setAnchorView(binding.fabAddEdit)
                            .show()
                    }
                    Status.LOADING ->{

                    }
                }
            }

        })


//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().popBackStack()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}