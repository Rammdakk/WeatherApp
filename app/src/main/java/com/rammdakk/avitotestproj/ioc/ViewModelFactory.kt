package com.rammdakk.avitotestproj.ioc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rammdakk.avitotestproj.connectivity.ConnectivityObserver
import com.rammdakk.avitotestproj.data.repository.WeatherRepository
import com.rammdakk.avitotestproj.domain.WeatherUseCase
import com.rammdakk.avitotestproj.ui.stateholders.WeatherViewModel

import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        WeatherViewModel::class.java -> WeatherViewModel(
            weatherUseCase,
            connectivityObserver
        )
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}
