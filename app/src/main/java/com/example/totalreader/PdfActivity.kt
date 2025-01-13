package com.example.totalreader

import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class PdfActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesContainer: View
    private val pdfFiles = mutableListOf<File>()
    private lateinit var pdfAdapter: PdfAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        initViews()
        setupRecyclerView()
        loadPdfFiles()
        setupBackNavigation()
    }

    // Initialize views
    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view_pdf)
        noFilesContainer = findViewById(R.id.no_files_container_pdf)
    }

    // Set up the RecyclerView and adapter
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        pdfAdapter = PdfAdapter(this, pdfFiles) { file ->
            openPdfFile(file) // Handle PDF file click
        }
        recyclerView.adapter = pdfAdapter
    }

    // Load PDF files from storage and update UI
    private fun loadPdfFiles() {
        val storageDir = Environment.getExternalStorageDirectory()
        searchForPdfFiles(storageDir)

        if (pdfFiles.isEmpty()) {
            showNoFilesFound()
        } else {
            showFilesList()
        }

        pdfAdapter.notifyDataSetChanged()
    }

    // Recursively search for PDF files in the directory
    private fun searchForPdfFiles(directory: File) {
        val files = directory.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchForPdfFiles(file) // Recursive call for directories
            } else if (file.extension.lowercase() == "pdf") {
                pdfFiles.add(file)
            }
        }
    }

    // Show "No Files Found" message
    private fun showNoFilesFound() {
        noFilesContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    // Show the RecyclerView with the list of files
    private fun showFilesList() {
        noFilesContainer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    // Handle custom back navigation with animation
    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        })
    }

    // Open the selected PDF file (placeholder logic)
    private fun openPdfFile(file: File) {
        // Add your logic to open the PDF file (e.g., using an external PDF viewer)
    }
}
