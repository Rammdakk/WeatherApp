package com.rammdakk.avitotestproj.ui.view.weatherFragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
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
    lateinit var locationManager: LocationManager

    fun setUpViews() {
        loadData()
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

    private fun loadData() {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                activity.baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity.baseContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.offlineMessage.postValue("Нед доступа к GPS")
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0F,
                locationListener
            )
        }
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

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            viewModel.updateTasks(
                lat = location.latitude,
                lon = location.longitude,
                cityName = null
            )
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}
