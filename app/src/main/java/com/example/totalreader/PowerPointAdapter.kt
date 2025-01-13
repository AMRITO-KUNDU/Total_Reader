package com.example.totalreader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class PowerPointAdapter(
    private val context: Context,
    private val files: List<File>,
    private val onFileClick: (File) -> Unit
) : RecyclerView.Adapter<PowerPointAdapter.PowerPointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerPointViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_powerpoint, parent, false)
        return PowerPointViewHolder(view)
    }

    override fun onBindViewHolder(holder: PowerPointViewHolder, position: Int) {
        val file = files[position]
        holder.fileNameTextView.text = file.name
        holder.itemView.setOnClickListener { onFileClick(file) }
    }

    override fun getItemCount(): Int = files.size

    inner class PowerPointViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileNameTextView: TextView = view.findViewById(R.id.powerpoint_file_name)
    }
}
