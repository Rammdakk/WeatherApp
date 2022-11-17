package com.rammdakk.avitotestproj.ui.view

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.rammdakk.avitotestproj.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 101
        )
        supportActionBar?.hide()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            101 -> {
                val binding = ActivityMainBinding.inflate(layoutInflater)
                val view = binding.root
                setContentView(view)
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}