package com.antont.notes.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.antont.notes.NoteDialog
import com.antont.notes.NotesApplication
import com.antont.notes.adapter.NotesRecyclerViewAdapter
import com.antont.notes.databinding.ActivityMainBinding
import com.antont.notes.db.entity.Note
import com.antont.notes.viewmodel.MainViewModel
import com.antont.notes.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as NotesApplication).repository)
    }

    private val noteListener = object : NotesRecyclerViewAdapter.OnNoteClickedListener {
        override fun onNoteClicked(note: Note) {
            NoteDialog.displayEdit(note, supportFragmentManager, noteDialogCallback)
        }
    }

    private val noteDialogCallback = object : NoteDialog.OnNoteDialogAction {
        override fun save(note: Note) {
            mainViewModel.insert(note)
        }

        override fun delete(id: Int) {
            mainViewModel.delete(id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        mainViewModel.allNotes.observe(this) {
            binding.content.notesRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            val adapter = NotesRecyclerViewAdapter(it, noteListener)
            binding.content.notesRecyclerView.adapter = adapter
        }

        binding.fab.setOnClickListener { view ->
            NoteDialog.displayCreate(supportFragmentManager, noteDialogCallback)
        }
    }
}