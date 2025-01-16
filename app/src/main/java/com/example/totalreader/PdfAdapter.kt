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

class PdfAdapter(
    private val context: Context,
    private val pdfFiles: List<File>,
    private val onFileClick: (File) -> Unit
) : RecyclerView.Adapter<PdfAdapter.PdfViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pdf_file, parent, false)
        return PdfViewHolder(view)
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        val file = pdfFiles[position]
        holder.bind(file)
        holder.itemView.setOnClickListener { onFileClick(file) }
    }

    override fun getItemCount(): Int = pdfFiles.size

    class PdfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.pdf_file_name)
        private val fileSizeTextView: TextView = itemView.findViewById(R.id.pdf_file_size)
        private val pdfIcon: ImageView = itemView.findViewById(R.id.pdf_icon)

        fun bind(file: File) {
            fileNameTextView.text = file.name
            fileSizeTextView.text = formatFileSize(file.length())
            pdfIcon.setImageResource(R.drawable.pdf) // Use your custom PDF icon
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
