package com.example.totalreader.fragments

import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import com.example.totalreader.ExcelActivity
import com.example.totalreader.PdfActivity
import com.example.totalreader.PowerPointActivity
import com.example.totalreader.R
import com.example.totalreader.TextActivity
import com.example.totalreader.WordActivity

class FilesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_files, container, false)

        // Set click listeners for each folder with scale animation
        setupFolderClickWithAnimation(view, R.id.pdf_folder, PdfActivity::class.java)
        setupFolderClickWithAnimation(view, R.id.word_folder, WordActivity::class.java)
        setupFolderClickWithAnimation(view, R.id.excel_folder, ExcelActivity::class.java)
        setupFolderClickWithAnimation(view, R.id.powerpoint_folder, PowerPointActivity::class.java)
        setupFolderClickWithAnimation(view, R.id.text_folder, TextActivity::class.java)

        return view
    }

    private fun setupFolderClickWithAnimation(view: View, folderId: Int, activityClass: Class<*>) {
        val folderView = view.findViewById<View>(folderId)
        folderView.setOnClickListener {
            // Scale animation on click
            ObjectAnimator.ofFloat(folderView, "scaleX", 1f, 0.95f, 1f).apply {
                duration = 150
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
            ObjectAnimator.ofFloat(folderView, "scaleY", 1f, 0.95f, 1f).apply {
                duration = 150
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }

            // Navigate with transition animation
            val intent = Intent(activity, activityClass)
            val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }
    }
}
