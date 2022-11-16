package com.rammdakk.avitotestproj.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.avitotestproj.data.model.WeatherPerDay
import com.rammdakk.avitotestproj.data.repository.WeatherRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    private val _weathers = MediatorLiveData<List<WeatherPerDay>>()
    private val _error = MediatorLiveData<Throwable>()

    val weathers: LiveData<List<WeatherPerDay>> = _weathers
    val error: LiveData<Throwable> = _error


    private val combineCoroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    init {
        _weathers.addSource(weatherRepository.weather) {
            updateWeatherLiveData()
        }
        _error.addSource(weatherRepository.error) {
            updateErrorLiveData()
        }
    }


    suspend fun loadAllTasks() {
        withContext(Dispatchers.Main) {
            weatherRepository.loadWeather()
        }
    }

    private fun updateWeatherLiveData() {
        combineCoroutineScope.launch {
            _weathers.postValue(weatherRepository.weather.value.orEmpty())
        }
    }


    private fun updateErrorLiveData() {
        combineCoroutineScope.launch {
            _error.postValue(weatherRepository.error.value)
        }
    }
}