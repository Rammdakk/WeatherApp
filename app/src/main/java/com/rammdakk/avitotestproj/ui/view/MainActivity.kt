package com.rammdakk.avitotestproj.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rammdakk.avitotestproj.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}