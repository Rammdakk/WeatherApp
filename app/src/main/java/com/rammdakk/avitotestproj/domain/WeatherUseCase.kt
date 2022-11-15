package com.rammdakk.avitotestproj.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.avitotestproj.data.WeatherToDate
import com.rammdakk.avitotestproj.data.repository.WeatherRepository

class WeatherUseCase (private val weatherRepository: WeatherRepository) {
    private val _weathers = MediatorLiveData<List<WeatherToDate>>()
    private val _error = MediatorLiveData<Throwable>()

    val weathers: LiveData<List<WeatherToDate>> = _weathers
    val error: LiveData<Throwable> = _error
}