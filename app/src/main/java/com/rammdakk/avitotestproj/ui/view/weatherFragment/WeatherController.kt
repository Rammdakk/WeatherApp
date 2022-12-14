package com.rammdakk.avitotestproj.ui.view.weatherFragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
    lateinit var sharedPreferences: SharedPreferences
    private var city: String? = null
    private var isMyLocation: Boolean = true

    fun setUp() {
        restoreData()
        loadData()
        binding.weekForecastRecycleView.addItemDecoration(
            DividerItemDecoration(
                binding.weekForecastRecycleView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        setUpErrorsHandling()
        setUpWeatherList()
        setUpButtons()
        setUpSwipeToRefresh()
        setUpSearchView()
    }

    private fun loadData() {
        if (city == null) {
            if (!loadDataByGps()) {
                viewModel.loadWeather(cityName = "London")
            }
        } else {
            viewModel.loadWeather(cityName = city)
        }
    }


    private fun restoreData() {
        sharedPreferences =
            activity.getSharedPreferences(activity.packageName, Context.MODE_PRIVATE)
        city = sharedPreferences.getString("cityName", null)
        isMyLocation = sharedPreferences.getBoolean("isMyLocation", true)
    }

    private fun setUpButtons() {
        binding.myLocationButton.setOnClickListener {
            isMyLocation = true
            loadDataByGps()
        }
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.loadWeather(cityName = query)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun loadDataByGps(): Boolean {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                activity.baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity.baseContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.offlineMessage.postValue("?????? ?????????????? ?? GPS. \n???????????????????? ???????????? ???????????????????? ?? ????????????????????")
            return false
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                600000L,
                0F,
                locationListener
            )
            viewModel.offlineMessage.postValue("")
            return true
        }
    }

    private fun setUpErrorsHandling() {
        viewModel.error.observe(lifecycleOwner) { data ->
            Toast.makeText(
                activity.applicationContext,
                "????????????: ${data.message}",
                Toast.LENGTH_SHORT
            ).show()
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
                loadData()
                bar?.dismiss()
            }
        }
    }

    private fun setUpWeatherList() {
        binding.weekForecastRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.weekForecastRecycleView.adapter = adapter
        viewModel.tasks.observe(lifecycleOwner) { newTasks ->
            adapter.submitList(newTasks)
            binding.swipeRefresh.isRefreshing = false
        }
        viewModel.cityInfo.observe(lifecycleOwner) { cityInfo ->
            binding.todayDayWeatherBodyTextView.text =
                activity.getString(R.string.temper_string, cityInfo.currentTemp)
            if (binding.todayDayWeatherHeaderTextView.text != cityInfo.name) {
                binding.todayDayWeatherHeaderTextView.text = cityInfo.name
                city = cityInfo.name
                isMyLocation = cityInfo.definedByGeo
                sharedPreferences.edit().putString("cityName", city)
                    .putBoolean("isMyLocation", isMyLocation).apply()
            }
        }
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadWeather(cityName = city)
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if (isMyLocation) {
                viewModel.loadWeather(
                    lat = location.latitude,
                    lon = location.longitude,
                    cityName = null
                )
            }
        }

        override fun onProviderEnabled(provider: String) {
            viewModel.offlineMessage.postValue("")
            loadDataByGps()
        }

        override fun onProviderDisabled(provider: String) {
            viewModel.offlineMessage.postValue("?????? ?????????????? ?? GPS")
        }
    }

}
