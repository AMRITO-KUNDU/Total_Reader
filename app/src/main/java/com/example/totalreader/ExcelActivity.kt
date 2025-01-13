package com.example.totalreader

import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class ExcelActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesContainer: View
    private val excelFiles = mutableListOf<File>()
    private lateinit var excelAdapter: ExcelAdapter

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

    // Set up the RecyclerView
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        excelAdapter = ExcelAdapter(this, excelFiles) { file ->
            openExcelFile(file) // Handle Excel file click
        }
        recyclerView.adapter = excelAdapter
    }

    // Load Excel files and manage UI visibility
    private fun loadExcelFiles() {
        val rootDir = Environment.getExternalStorageDirectory()
        val extensions = arrayOf("xls", "xlsx")
        searchForExcelFiles(rootDir, extensions)

        if (excelFiles.isEmpty()) {
            showNoFilesFound()
        } else {
            showFilesList()
        }

        excelAdapter.notifyDataSetChanged()
    }

    // Recursive search for Excel files in the directory
    private fun searchForExcelFiles(directory: File, extensions: Array<String>) {
        val files = directory.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchForExcelFiles(file, extensions)
            } else if (file.extension.lowercase() in extensions) {
                excelFiles.add(file)
            }
        }
    }

    // Show "No Files Found" container
    private fun showNoFilesFound() {
        noFilesContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    // Show the RecyclerView with the list of Excel files
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

    // Placeholder to open Excel files
    private fun openExcelFile(file: File) {
        // Implement file opening logic (e.g., launch an external Excel viewer)
    }
}

