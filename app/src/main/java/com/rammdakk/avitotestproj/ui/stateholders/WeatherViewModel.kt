package com.rammdakk.avitotestproj.ui.stateholders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.avitotestproj.connectivity.ConnectivityObserver
import com.rammdakk.avitotestproj.data.repository.WeatherRepository
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
    val offlineMessage: MutableLiveData<String> = MutableLiveData("")


    init {
        offlineMessage.postValue("Ошибка: Нет доступа к интернету")
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available) {
                weatherUseCase.loadAllTasks()
                offlineMessage.postValue("")
            } else {
                offlineMessage.postValue("Ошибка: Нет доступа к интернету")
            }
        }.launchIn(viewModelScope)
    }

    fun updateTasks() {
        viewModelScope.launch {
            weatherUseCase.loadAllTasks()
        }
    }
}