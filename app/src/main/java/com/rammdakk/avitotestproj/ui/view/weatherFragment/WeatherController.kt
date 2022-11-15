package com.rammdakk.avitotestproj.ui.view.weatherFragment

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rammdakk.avitotestproj.R
import com.rammdakk.avitotestproj.databinding.FragmentWeatherBinding
import com.rammdakk.avitotestproj.ui.stateholders.WeatherViewModel

class WeatherController(
    private val activity: Activity,
    rootView: View,
    private val binding: FragmentWeatherBinding
    private val adapter: WeatherAdapter,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: WeatherViewModel,
) {
    private var bar: Snackbar? = null
    fun setUpViews() {
        setUpErrorsHandling()
        setUpTasksList()
        setUpButtons()
    }

    private fun setUpErrorsHandling() {
        viewModel.error.observe(lifecycleOwner) { data ->
            Toast.makeText(activity.applicationContext, "Ошибка: $data", Toast.LENGTH_SHORT).show()
        }
        viewModel.offlineMessage.observe(lifecycleOwner) { data ->
            if (data.isNotEmpty()) {
                bar?.dismiss()
                bar = Snackbar.make(
                    activity.findViewById(R.id.fragment_container_view),
                    data,
                    Snackbar.LENGTH_INDEFINITE
                )
                bar?.show()
            } else {
                bar?.dismiss()
            }
        }
    }

    private fun setUpTasksList() {
        binding.weekForecastRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.weekForecastRecycleView.adapter = adapter
        viewModel.tasks.observe(lifecycleOwner) { newTasks ->
            adapter.submitList(newTasks)
        }
    }


    private fun setUpButtons() {
        TODO("Add refresh button")
    }

}