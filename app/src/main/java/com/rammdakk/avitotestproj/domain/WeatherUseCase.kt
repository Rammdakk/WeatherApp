package com.rammdakk.avitotestproj.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.avitotestproj.data.model.CityInfo
import com.rammdakk.avitotestproj.data.model.WeatherPerDay
import com.rammdakk.avitotestproj.data.repository.WeatherRepository
import com.rammdakk.avitotestproj.data.restApi.Units
import kotlinx.coroutines.*
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    private val _weathers = MediatorLiveData<List<WeatherPerDay>>()
    private val _cityInfo = MediatorLiveData<CityInfo>()
    private val _error = MediatorLiveData<Throwable>()

    val weathers: LiveData<List<WeatherPerDay>> = _weathers
    val error: LiveData<Throwable> = _error
    val cityInfo: LiveData<CityInfo> = _cityInfo


    private val combineCoroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    init {
        _weathers.addSource(weatherRepository.weatherData) {
            updateWeatherLiveData()
        }
        _error.addSource(weatherRepository.error) {
            updateErrorLiveData()
        }
        _cityInfo.addSource(weatherRepository.cityInfo){
            updateCity()
        }
    }


    suspend fun loadWeather(
        cnt: Int = 40,
        units: Units = Units.metric,
        lat: Double = 0.0,
        lon: Double = 0.0,
        cityName: String?
    ) {
        withContext(Dispatchers.Main) {
            weatherRepository.loadWeather(cnt, units, lat, lon, cityName)
        }
    }

    private fun updateWeatherLiveData() {
        combineCoroutineScope.launch {
            _weathers.postValue(weatherRepository.weatherData.value.orEmpty())
        }
    }

    private fun updateCity() {
        combineCoroutineScope.launch {
            _cityInfo.postValue(weatherRepository.cityInfo.value)
        }
    }


    private fun updateErrorLiveData() {
        combineCoroutineScope.launch {
            _error.postValue(weatherRepository.error.value)
        }
    }
}