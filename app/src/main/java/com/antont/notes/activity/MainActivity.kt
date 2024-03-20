package com.antont.notes.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.antont.notes.NotesApplication
import com.antont.notes.R
import com.antont.notes.adapter.NotesRecyclerViewAdapter
import com.antont.notes.databinding.ActivityMainBinding
import com.antont.notes.viewmodel.MainViewModel
import com.antont.notes.viewmodel.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as NotesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        mainViewModel.allNotes.observe(this) {
            binding.content.notesRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            val adapter = NotesRecyclerViewAdapter(it)
            binding.content.notesRecyclerView.adapter = adapter
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }
}