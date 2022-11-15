package com.rammdakk.avitotestproj.ui.stateholders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.avitotestproj.domain.WeatherUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val taskRepository: TaskRepository,
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
                taskRepository.updateAllTasks()
                offlineMessage.postValue("")
            } else {
                offlineMessage.postValue("Ошибка: Нет доступа к интернету")
            }
        }.launchIn(viewModelScope)
    }

    fun updateTasks() {
        viewModelScope.launch {
            tasksUseCase.loadAllTasks()
        }
    }
}