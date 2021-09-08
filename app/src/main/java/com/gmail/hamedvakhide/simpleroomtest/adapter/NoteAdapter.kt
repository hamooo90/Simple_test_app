package com.gmail.hamedvakhide.simpleroomtest.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hamedvakhide.simpleroomtest.R
import com.gmail.hamedvakhide.simpleroomtest.data.Note
import com.gmail.hamedvakhide.simpleroomtest.ui.NoteFragmentDirections
import com.google.android.material.textview.MaterialTextView
import javax.inject.Inject

class NoteAdapter @Inject constructor() : ListAdapter<Note,NoteAdapter.NoteViewHolder>(COMPARATOR) {
    class NoteViewHolder private constructor(itemView:View): RecyclerView.ViewHolder(itemView){
        private val title: MaterialTextView = itemView.findViewById(R.id.txt_title_item)
        private val content: MaterialTextView = itemView.findViewById(R.id.txt_content_item)
        fun bind(note: Note?) {
            title.text = note?.title
            content.text = note?.content
            itemView.setOnClickListener {
                it.findNavController().navigate(
                    NoteFragmentDirections.actionNoteFragmentToAddEditFragment(note)
                )
            }
        }

        companion object{
            fun from(parent: ViewGroup): NoteViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.note_item,parent,false)
                return NoteViewHolder(itemView)
            }
        }
    }

        companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        note.apply {
            holder.bind(this)
        }

    }
//
//    private var onItemClickListener: ((Note) -> Unit)? = null
//    fun setOnItemClickListener(listener: (Note) -> Unit) {
//        onItemClickListener = listener
//
//    }
}

