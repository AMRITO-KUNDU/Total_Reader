package com.example.totalreader

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.totalreader.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val MANAGE_STORAGE_PERMISSION_REQUEST_CODE = 123
        const val PREFS_NAME = "AppPreferences"
        const val KEY_PERMISSION_GRANTED = "StoragePermissionGranted"
        const val KEY_KEEP_SCREEN_ON = "KeepScreenOn"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Apply the "Keep Screen On" setting
        applyKeepScreenOnSetting()

        // Set the default fragment
        loadFragment(HomeFragment())
        updateBottomNavIcons(R.id.nav_home)

        // Check permissions and show tip if required
        if (!isStoragePermissionGranted()) {
            showPermissionTipIfNeeded()
        }

        // Bottom navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    updateBottomNavIcons(R.id.nav_home)
                }
                R.id.nav_files -> {
                    loadFragment(FilesFragment())
                    updateBottomNavIcons(R.id.nav_files)
                }
                R.id.nav_bookmark -> {
                    loadFragment(BookmarkFragment())
                    updateBottomNavIcons(R.id.nav_bookmark)
                }
                R.id.nav_tools -> {
                    loadFragment(ToolsFragment())
                    updateBottomNavIcons(R.id.nav_tools)
                }
                R.id.nav_settings -> {
                    loadFragment(SettingsFragment())
                    updateBottomNavIcons(R.id.nav_settings)
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun updateBottomNavIcons(selectedItemId: Int) {
        bottomNavigationView.menu.findItem(R.id.nav_home).setIcon(R.drawable.home)
        bottomNavigationView.menu.findItem(R.id.nav_files).setIcon(R.drawable.files)
        bottomNavigationView.menu.findItem(R.id.nav_bookmark).setIcon(R.drawable.bookmark)
        bottomNavigationView.menu.findItem(R.id.nav_tools).setIcon(R.drawable.tools)
        bottomNavigationView.menu.findItem(R.id.nav_settings).setIcon(R.drawable.settings)

        when (selectedItemId) {
            R.id.nav_home -> bottomNavigationView.menu.findItem(R.id.nav_home).setIcon(R.drawable.home_selected)
            R.id.nav_files -> bottomNavigationView.menu.findItem(R.id.nav_files).setIcon(R.drawable.files_selected)
            R.id.nav_bookmark -> bottomNavigationView.menu.findItem(R.id.nav_bookmark).setIcon(R.drawable.bookmark_selected)
            R.id.nav_tools -> bottomNavigationView.menu.findItem(R.id.nav_tools).setIcon(R.drawable.tools_selected)
            R.id.nav_settings -> bottomNavigationView.menu.findItem(R.id.nav_settings).setIcon(R.drawable.settings_selected)
        }
    }

    private fun showPermissionTipIfNeeded() {
        val permissionShown = sharedPreferences.getBoolean(KEY_PERMISSION_GRANTED, false)
        if (!permissionShown) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Storage Access Required")
            builder.setMessage("This app needs access to your device's storage to manage files and display content properly. Please allow the permission.")
            builder.setCancelable(false)
            builder.setPositiveButton("Continue") { dialog, _ ->
                dialog.dismiss()
                checkAndRequestPermissions()
            }
            builder.create().show()
        } else {
            checkAndRequestPermissions()
        }
    }

    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!android.os.Environment.isExternalStorageManager()) {
                requestAllFilesAccessPermission()
            } else {
                savePermissionGranted()
            }
        } else {
            requestLegacyStoragePermissions()
        }
    }

    private fun requestAllFilesAccessPermission() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, MANAGE_STORAGE_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to request file access permission", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestLegacyStoragePermissions() {
        if (!isStoragePermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                MANAGE_STORAGE_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            android.os.Environment.isExternalStorageManager()
        } else {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }
    }

    private fun savePermissionGranted() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_PERMISSION_GRANTED, true)
        editor.apply()
    }

    private fun applyKeepScreenOnSetting() {
        val keepScreenOn = sharedPreferences.getBoolean(KEY_KEEP_SCREEN_ON, false)
        if (keepScreenOn) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MANAGE_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                savePermissionGranted()
                Toast.makeText(this, "Storage access granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage access denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MANAGE_STORAGE_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (android.os.Environment.isExternalStorageManager()) {
                    savePermissionGranted()
                    Toast.makeText(this, "All files access granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "All files access denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
