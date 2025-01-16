package com.mobbelldev.diaryapp.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobbelldev.diaryapp.R
import com.mobbelldev.diaryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                showPermissionRationaleDialog()

                // Redirect to Settings if permission was denied
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                        // Permission permanently denied (Don't ask again)
                        Toast.makeText(
                            this,
                            "Permission permanently denied. Please enable notification permission in Settings.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.fromParts("package", packageName, null)
                        startActivity(intent)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            Toast.makeText(
                this,
                "No need to request notification permission on this device.",
                Toast.LENGTH_SHORT
            ).show()
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_diary, R.id.navigation_latest, R.id.navigation_settings
            )
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Notification Permission Required")
            .setMessage("This app requires notification permissions. Please allow the permission.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(
                "Cancel"
            ) { _, _ ->
                this@MainActivity.finish()
                Toast.makeText(
                    this,
                    "Permission denied, Please allow the permission to use the app.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }
}