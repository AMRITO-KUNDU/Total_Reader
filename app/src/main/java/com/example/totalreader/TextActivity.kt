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

class TextActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesContainer: LinearLayout
    private val textFiles = mutableListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        initViews()
        setupRecyclerView()
        loadTextFiles()
        setupBackNavigation()
    }

    // Initialize views
    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view_txt)
        noFilesContainer = findViewById(R.id.no_files_container_txt)
    }

    // Set up RecyclerView
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TextAdapter(this, textFiles) { file ->
            openTextFile(file) // Handle text file click
        }

    }

    // Load text files and toggle visibility of UI elements
    private fun loadTextFiles() {
        val storageDir = Environment.getExternalStorageDirectory()
        searchForTextFiles(storageDir)

        if (textFiles.isEmpty()) {
            showNoFilesFound()
        } else {
            showFilesList()
        }
    }

    // Recursively search for text files in a directory
    private fun searchForTextFiles(directory: File) {
        val files = directory.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchForTextFiles(file) // Recursive call
            } else if (file.name.endsWith(".txt", ignoreCase = true)) {
                textFiles.add(file)
            }
        }
    }

    // Show the "No Text Files Found" container
    private fun showNoFilesFound() {
        noFilesContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    // Show the RecyclerView with the list of text files
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

    // Placeholder for opening a text file
    private fun openTextFile(file: File) {
        // Implement file opening logic here (e.g., display content in a new activity or dialog)
    }
}
