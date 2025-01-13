package com.example.totalreader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class ExcelAdapter(
    private val context: Context,
    private val files: List<File>,
    private val onFileClick: (File) -> Unit
) : RecyclerView.Adapter<ExcelAdapter.ExcelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExcelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_excel_file, parent, false)
        return ExcelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExcelViewHolder, position: Int) {
        val file = files[position]
        holder.fileNameTextView.text = file.name

        holder.itemView.setOnClickListener {
            onFileClick(file) // Handle file click
        }
    }

    override fun getItemCount(): Int = files.size

    class ExcelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.file_name_text)
    }
}
