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

class ExcelActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesContainer: LinearLayout
    private val excelFiles = mutableListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excel)

        initViews()
        setupRecyclerView()
        loadExcelFiles()
        setupBackNavigation()
    }

    // Initialize views
    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view_excel)
        noFilesContainer = findViewById(R.id.no_files_container_excel)
    }

    // Set up RecyclerView
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ExcelAdapter(this, excelFiles) { file ->
            openExcelFile(file) // Handle Excel file click
        }
    }

    // Load Excel files and toggle visibility of UI elements
    private fun loadExcelFiles() {
        val storageDir = Environment.getExternalStorageDirectory()
        searchForExcelFiles(storageDir)

        if (excelFiles.isEmpty()) {
            showNoFilesFound()
        } else {
            showFilesList()
        }
    }

    // Recursively search for Excel files in a directory
    private fun searchForExcelFiles(directory: File) {
        val files = directory.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchForExcelFiles(file) // Recursive call
            } else if (file.name.endsWith(".xls", ignoreCase = true) ||
                file.name.endsWith(".xlsx", ignoreCase = true)) {
                excelFiles.add(file)
            }
        }
    }

    // Show the "No Excel Files Found" container
    private fun showNoFilesFound() {
        noFilesContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    // Show the RecyclerView with the list of Excel files
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

    // Placeholder for opening an Excel file
    private fun openExcelFile(file: File) {
        // Implement file opening logic here (e.g., launch an Excel viewer)
    }
}
