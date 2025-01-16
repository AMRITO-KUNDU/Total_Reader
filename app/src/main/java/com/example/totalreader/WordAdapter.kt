package com.example.totalreader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.text.DecimalFormat

class WordAdapter(
    private val context: Context,
    private val wordFiles: List<File>,
    private val onFileClick: (File) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_word_file, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val file = wordFiles[position]
        holder.bind(file)
        holder.itemView.setOnClickListener { onFileClick(file) }
    }

    override fun getItemCount(): Int = wordFiles.size

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.word_file_name)
        private val fileSizeTextView: TextView = itemView.findViewById(R.id.word_file_size)
        private val wordIcon: ImageView = itemView.findViewById(R.id.word_icon)

        fun bind(file: File) {
            fileNameTextView.text = file.name
            fileSizeTextView.text = formatFileSize(file.length())
            wordIcon.setImageResource(R.drawable.word) // Use your custom Word icon
        }

        private fun formatFileSize(size: Long): String {
            val kb = size / 1024.0
            val mb = kb / 1024.0
            return if (mb >= 1) {
                "${DecimalFormat("#.##").format(mb)} MB"
            } else {
                "${DecimalFormat("#.##").format(kb)} KB"
            }
        }
    }
}
