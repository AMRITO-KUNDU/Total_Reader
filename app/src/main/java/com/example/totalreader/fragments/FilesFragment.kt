package com.example.totalreader.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.totalreader.*
import com.example.totalreader.R
import android.app.ActivityOptions

class FilesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_files, container, false)

        // Set click listeners for each folder with animation
        view.findViewById<View>(R.id.pdf_folder).setOnClickListener {
            val intent = Intent(activity, PdfActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        view.findViewById<View>(R.id.word_folder).setOnClickListener {
            val intent = Intent(activity, WordActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        view.findViewById<View>(R.id.excel_folder).setOnClickListener {
            val intent = Intent(activity, ExcelActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        view.findViewById<View>(R.id.powerpoint_folder).setOnClickListener {
            val intent = Intent(activity, PowerPointActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        view.findViewById<View>(R.id.text_folder).setOnClickListener {
            val intent = Intent(activity, TextActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        return view
    }
}
