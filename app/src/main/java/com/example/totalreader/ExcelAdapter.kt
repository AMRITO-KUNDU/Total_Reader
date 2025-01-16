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

class ExcelAdapter(
    private val context: Context,
    private val excelFiles: List<File>,
    private val onFileClick: (File) -> Unit
) : RecyclerView.Adapter<ExcelAdapter.ExcelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExcelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_excel_file, parent, false)
        return ExcelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExcelViewHolder, position: Int) {
        val file = excelFiles[position]
        holder.bind(file)
        holder.itemView.setOnClickListener { onFileClick(file) }
    }

    override fun getItemCount(): Int = excelFiles.size

    class ExcelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.excel_file_name)
        private val fileSizeTextView: TextView = itemView.findViewById(R.id.excel_file_size)
        private val excelIcon: ImageView = itemView.findViewById(R.id.excel_icon)

        fun bind(file: File) {
            fileNameTextView.text = file.name
            fileSizeTextView.text = formatFileSize(file.length())
            excelIcon.setImageResource(R.drawable.excel) // Use your custom Excel icon
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
