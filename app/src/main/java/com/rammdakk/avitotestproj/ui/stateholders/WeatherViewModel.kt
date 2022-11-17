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


    init {
        offlineMessage.postValue("Ошибка: Нет доступа к интернету")
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available) {
                offlineMessage.postValue("")
            } else {
                offlineMessage.postValue("Ошибка: Нет доступа к интернету")
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
            weatherUseCase.loadWeather(cnt, units, lat, lon, cityName)
        }
    }
}