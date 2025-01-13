package com.example.totalreader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class WordAdapter(
    private val wordFiles: List<File>,
    private val onItemClick: (File) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val file = wordFiles[position]
        holder.bind(file)
    }

    override fun getItemCount(): Int = wordFiles.size

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(file: File) {
            fileNameTextView.text = file.name
            itemView.setOnClickListener { onItemClick(file) }
        }
    }
}
