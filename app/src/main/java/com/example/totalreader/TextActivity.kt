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
    private lateinit var adapter: TextAdapter
    private val textFiles = mutableListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        recyclerView = findViewById(R.id.recycler_view_text)
        noFilesContainer = findViewById(R.id.no_files_container)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TextAdapter(this, textFiles)
        recyclerView.adapter = adapter

        // Load text files and show/hide UI elements
        loadTextFiles()

        // Handle back navigation with animation
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        })
    }

    private fun loadTextFiles() {
        val storageDir = Environment.getExternalStorageDirectory()
        searchForTextFiles(storageDir)

        if (textFiles.isNotEmpty()) {
            noFilesContainer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            noFilesContainer.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }

        adapter.notifyDataSetChanged()
    }

    private fun searchForTextFiles(directory: File) {
        val files = directory.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchForTextFiles(file)
            } else if (file.name.endsWith(".txt", true)) {
                textFiles.add(file)
            }
        }
    }
}
