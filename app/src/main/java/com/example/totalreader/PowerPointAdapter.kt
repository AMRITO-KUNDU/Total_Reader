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

class PowerPointAdapter(
    private val context: Context,
    private val pptFiles: List<File>,
    private val onFileClick: (File) -> Unit
) : RecyclerView.Adapter<PowerPointAdapter.PowerPointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerPointViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ppt_file, parent, false)
        return PowerPointViewHolder(view)
    }

    override fun onBindViewHolder(holder: PowerPointViewHolder, position: Int) {
        val file = pptFiles[position]
        holder.bind(file)
        holder.itemView.setOnClickListener { onFileClick(file) }
    }

    override fun getItemCount(): Int = pptFiles.size

    class PowerPointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.ppt_file_name)
        private val fileSizeTextView: TextView = itemView.findViewById(R.id.ppt_file_size)
        private val pptIcon: ImageView = itemView.findViewById(R.id.ppt_icon)

        fun bind(file: File) {
            fileNameTextView.text = file.name
            fileSizeTextView.text = formatFileSize(file.length())
            pptIcon.setImageResource(R.drawable.powerpoint) // Use your custom PowerPoint icon
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
