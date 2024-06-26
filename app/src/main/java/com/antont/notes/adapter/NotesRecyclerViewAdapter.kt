package com.antont.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antont.notes.databinding.RecyclerViewNoteBinding
import com.antont.notes.db.entity.Note

class NotesRecyclerViewAdapter(
    private val allNotes: List<Note>,
    private val listener: OnNoteClickedListener? = null
) :
    RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecyclerViewNoteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(allNotes[position])
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    class NoteHolder(
        private val binding: RecyclerViewNoteBinding,
        private val listener: OnNoteClickedListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.root.setOnClickListener {
                listener?.onNoteClicked(note)
            }
            binding.title.text = note.title
            binding.content.text = note.content
        }
    }

    interface OnNoteClickedListener {
        fun onNoteClicked(note: Note)
    }
}