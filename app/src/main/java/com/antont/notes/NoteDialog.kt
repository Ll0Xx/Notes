package com.antont.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.antont.notes.adapter.NotesRecyclerViewAdapter
import com.antont.notes.databinding.NoteDialogBinding
import com.antont.notes.db.entity.Note


class NoteDialog(
    private var title: String,
    private var note: Note? = null,
    private var isDeleteButtonVisible: Boolean = false,
    private val listener: OnNoteDialogAction? = null
) : DialogFragment() {
    private lateinit var binding: NoteDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Notes_FullScreenDialog);
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.let { w ->
                w.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                w.setWindowAnimations(R.style.Theme_Notes_Slide)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = NoteDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            with(toolbar) {
                setNavigationOnClickListener { dismiss() }
                setTitle(this@NoteDialog.title)
                inflateMenu(R.menu.note_dialog)
                menu.findItem(R.id.action_delete).setVisible(isDeleteButtonVisible)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_delete -> {
                            note?.let {
                                listener?.delete(it.uid)
                            } ?: run {
                                Toast.makeText( view.context, "Error during saving note", Toast.LENGTH_SHORT).show()
                            }
                        }

                        R.id.action_save -> {
                            val id:Int = note?.uid ?: 0
                            val title = binding.content.noteTitleEditText.text.toString()
                            val content = binding.content.noteContentEditText.text.toString()
                            listener?.save(Note(id, title, content))
                        }
                    }
                    dismiss()
                    return@setOnMenuItemClickListener true
                }
            }
            note?.let {
                binding.content.noteTitleLayout.editText?.setText(it.title)
                binding.content.noteContentLayout.editText?.setText(it.content)
            }
        }
    }

    companion object {
        private const val TAG = "example_dialog"
        fun displayCreate(
            fragmentManager: FragmentManager,
            listener: OnNoteDialogAction? = null
        ): NoteDialog {
            val exampleDialog = NoteDialog("Create Note", listener = listener)
            exampleDialog.show(fragmentManager, TAG)
            return exampleDialog
        }

        fun displayEdit(
            note: Note,
            fragmentManager: FragmentManager,
            listener: OnNoteDialogAction? = null
        ): NoteDialog {
            val exampleDialog = NoteDialog("Edit Note", note, isDeleteButtonVisible = true, listener = listener)
            exampleDialog.show(fragmentManager, TAG)
            return exampleDialog
        }
    }

    interface OnNoteDialogAction {
        fun save(note: Note)
        fun delete(id: Int)
    }
}