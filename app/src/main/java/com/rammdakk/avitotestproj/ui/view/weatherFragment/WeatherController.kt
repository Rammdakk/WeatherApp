package com.rammdakk.avitotestproj.ui.view.weatherFragment

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rammdakk.avitotestproj.R
import com.rammdakk.avitotestproj.databinding.FragmentWeatherBinding
import com.rammdakk.avitotestproj.ui.stateholders.WeatherViewModel

class WeatherController(
    private val activity: Activity,
    private val binding: FragmentWeatherBinding,
    private val adapter: WeatherAdapter,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: WeatherViewModel,
) {
    private var bar: Snackbar? = null
    fun setUpViews() {
        binding.weekForecastRecycleView.addItemDecoration(
            DividerItemDecoration(
                binding.weekForecastRecycleView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        setUpErrorsHandling()
        setUpTasksList()
        setUpSwipeToRefresh()
    }

    private fun setUpErrorsHandling() {
        viewModel.error.observe(lifecycleOwner) { data ->
            Toast.makeText(activity.applicationContext, "Ошибка: $data", Toast.LENGTH_SHORT).show()
            Log.d("errr", data.toString())
            binding.swipeRefresh.isRefreshing = false
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
            binding.swipeRefresh.isRefreshing = false

        }
        viewModel.cityInfo.observe(lifecycleOwner) { cityInfo ->
            binding.todayDayWeatherBodyTextView.text = "${cityInfo.curentTemp}°"
            binding.todayDayWeatherHeaderTextView.text = cityInfo.name
        }
    }


    private fun setUpSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.updateTasks(cnt = 40, cityName = "Moscow")
        }
    }

}