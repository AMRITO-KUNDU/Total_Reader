package com.example.totalreader

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class WordActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesContainer: LinearLayout
    private val wordFiles = mutableListOf<File>()

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

    // Set up RecyclerView
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = WordAdapter(this, wordFiles) { file ->
            openWordFile(file) // Handle Word file click
        }
    }

    // Load Word files and toggle visibility of UI elements
    private fun loadWordFiles() {
        val storageDir = Environment.getExternalStorageDirectory()
        searchForWordFiles(storageDir)

        if (wordFiles.isEmpty()) {
            showNoFilesFound()
        } else {
            showFilesList()
        }
    }

    // Recursively search for Word files in a directory
    private fun searchForWordFiles(directory: File) {
        val files = directory.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchForWordFiles(file) // Recursive call
            } else if (file.name.endsWith(".doc", ignoreCase = true) ||
                file.name.endsWith(".docx", ignoreCase = true)) {
                wordFiles.add(file)
            }
        }
    }

    // Show the "No Word Files Found" container
    private fun showNoFilesFound() {
        noFilesContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    // Show the RecyclerView with the list of Word files
    private fun showFilesList() {
        noFilesContainer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    // Handle back navigation with custom animation
    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        })
    }

    // Placeholder for opening a Word file
    private fun openWordFile(file: File) {
        // Implement file opening logic here (e.g., launch a Word viewer)
    }
}
