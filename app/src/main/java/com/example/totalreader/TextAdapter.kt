package com.example.totalreader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class TextAdapter(
    private val context: Context,
    private val textFiles: List<File>
) : RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameText: TextView = itemView.findViewById(R.id.file_name_text)

        init {
            itemView.setOnClickListener {
                // Handle item click
                val file = textFiles[adapterPosition]
                // Open the file or perform desired action
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_text_file, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val file = textFiles[position]
        holder.fileNameText.text = file.name
    }

    override fun getItemCount(): Int {
        return textFiles.size
    }
}
