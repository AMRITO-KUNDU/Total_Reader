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

class PowerPointActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesContainer: LinearLayout
    private val pptFiles = mutableListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_powerpoint)

        initViews()
        setupRecyclerView()
        loadPowerPointFiles()
        setupBackNavigation()
    }

    // Initialize views
    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view_ppt)
        noFilesContainer = findViewById(R.id.no_files_container_ppt)
    }

    // Set up RecyclerView
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PowerPointAdapter(this, pptFiles) { file ->
            openPowerPointFile(file) // Handle PowerPoint file click
        }
    }

    // Load PowerPoint files and toggle visibility of UI elements
    private fun loadPowerPointFiles() {
        val storageDir = Environment.getExternalStorageDirectory()
        searchForPowerPointFiles(storageDir)

        if (pptFiles.isEmpty()) {
            showNoFilesFound()
        } else {
            showFilesList()
        }
    }

    // Recursively search for PowerPoint files in a directory
    private fun searchForPowerPointFiles(directory: File) {
        val files = directory.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchForPowerPointFiles(file) // Recursive call
            } else if (file.name.endsWith(".ppt", ignoreCase = true) ||
                file.name.endsWith(".pptx", ignoreCase = true)) {
                pptFiles.add(file)
            }
        }
    }

    // Show the "No PowerPoint Files Found" container
    private fun showNoFilesFound() {
        noFilesContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    // Show the RecyclerView with the list of PowerPoint files
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

    // Placeholder for opening a PowerPoint file
    private fun openPowerPointFile(file: File) {
        // Implement file opening logic here (e.g., launch a PowerPoint viewer)
    }
}
