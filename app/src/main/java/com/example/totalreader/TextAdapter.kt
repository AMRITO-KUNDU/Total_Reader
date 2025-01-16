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

class TextAdapter(
    private val context: Context,
    private val textFiles: List<File>,
    private val onFileClick: (File) -> Unit
) : RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_text_file, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val file = textFiles[position]
        holder.bind(file)
        holder.itemView.setOnClickListener { onFileClick(file) }
    }

    override fun getItemCount(): Int = textFiles.size

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.text_file_name)
        private val fileSizeTextView: TextView = itemView.findViewById(R.id.text_file_size)
        private val textIcon: ImageView = itemView.findViewById(R.id.text_icon)

        fun bind(file: File) {
            fileNameTextView.text = file.name
            fileSizeTextView.text = formatFileSize(file.length())
            textIcon.setImageResource(R.drawable.text) // Use your custom Text file icon
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
