package com.example.totalreader

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class PowerPointActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesContainer: View
    private lateinit var adapter: PowerPointAdapter
    private val powerPointFiles = mutableListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_powerpoint)

        recyclerView = findViewById(R.id.recycler_view_powerpoint)
        noFilesContainer = findViewById(R.id.no_files_container_powerpoint)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter
        adapter = PowerPointAdapter(this, powerPointFiles) { file ->
            Log.d("PowerPointActivity", "Clicked PowerPoint file: ${file.name}")
            // TODO: Add your logic to open or share the PowerPoint file here
        }
        recyclerView.adapter = adapter

        // Load PowerPoint files
        loadPowerPointFiles()

        // Handle custom back navigation
        handleBackNavigation()
    }

    private fun loadPowerPointFiles() {
        val rootDir = Environment.getExternalStorageDirectory()
        val extensions = arrayOf("ppt", "pptx")
        searchFiles(rootDir, extensions)

        if (powerPointFiles.isEmpty()) {
            noFilesContainer.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            noFilesContainer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        adapter.notifyDataSetChanged()
    }

    private fun searchFiles(dir: File, extensions: Array<String>) {
        val files = dir.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                searchFiles(file, extensions)
            } else if (file.extension in extensions) {
                powerPointFiles.add(file)
            }
        }
    }

    private fun handleBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        })
    }
}
