package com.example.totalreader

import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class WordActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesContainer: View
    private val wordFiles = mutableListOf<File>()
    private lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)

        initViews()
        setupRecyclerView()
        loadWordFiles()
        setupBackNavigation()
    }

    // Initialize views
    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view_word)
        noFilesContainer = findViewById(R.id.no_files_container_word)
    }

    // Set up the RecyclerView
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordAdapter = WordAdapter(wordFiles) { file ->
            openWordFile(file) // Handle Word file click
        }
        recyclerView.adapter = wordAdapter
    }

    // Load Word files and manage UI visibility
    private fun loadWordFiles() {
        val rootDir = Environment.getExternalStorageDirectory()
        val extensions = arrayOf("doc", "docx")
        searchForWordFiles(rootDir, extensions)

        if (wordFiles.isEmpty()) {
            showNoFilesFound()
        } else {
            showFilesList()
        }

        wordAdapter.notifyDataSetChanged()
    }

    // Recursive search for Word files in the directory
    private fun searchForWordFiles(directory: File, extensions: Array<String>) {
        val files = directory.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchForWordFiles(file, extensions)
            } else if (file.extension.lowercase() in extensions) {
                wordFiles.add(file)
            }
        }
    }

    // Show "No Files Found" container
    private fun showNoFilesFound() {
        noFilesContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    // Show the RecyclerView with the list of Word files
    private fun showFilesList() {
        noFilesContainer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    // Handle back navigation with animation
    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        })
    }

    // Placeholder to open Word files
    private fun openWordFile(file: File) {
        // Implement file opening logic (e.g., launch an external Word viewer)
    }
}
