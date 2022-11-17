package com.rammdakk.avitotestproj.ui.stateholders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.avitotestproj.connectivity.ConnectivityObserver
import com.rammdakk.avitotestproj.data.restApi.Units
import com.rammdakk.avitotestproj.domain.WeatherUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherUseCase: WeatherUseCase,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {
    val tasks = weatherUseCase.weathers
    val error = weatherUseCase.error
    val cityInfo = weatherUseCase.cityInfo
    val offlineMessage: MutableLiveData<String> = MutableLiveData("")
    var isOnline: Boolean = true


    init {
        offlineMessage.postValue("Ошибка: Нет доступа к интернету")
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available) {
                offlineMessage.postValue("")
                isOnline = true
            } else {
                offlineMessage.postValue("Ошибка: Нет доступа к интернету")
                isOnline = false

            }
        }.launchIn(viewModelScope)
    }

    fun loadWeather(
        cnt: Int = 40,
        units: Units = Units.metric,
        lat: Double = 0.0,
        lon: Double = 0.0,
        cityName: String?
    ) {
        viewModelScope.launch {
            if (isOnline)
                weatherUseCase.loadWeather(cnt, units, lat, lon, cityName)
        }
    }
}