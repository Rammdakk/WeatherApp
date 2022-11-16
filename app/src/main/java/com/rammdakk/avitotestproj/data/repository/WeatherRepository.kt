package com.rammdakk.avitotestproj.data.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rammdakk.avitotestproj.data.datasource.DataSource
import com.rammdakk.avitotestproj.data.model.WeatherPerDay
import com.rammdakk.avitotestproj.data.model.WeatherToDate
import com.rammdakk.avitotestproj.data.restApi.Units
import com.rammdakk.todo.ioc.ApplicationComponentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationComponentScope
class WeatherRepository @Inject constructor(
    private val dataSource: DataSource
) {
    private val _data: MutableLiveData<List<WeatherPerDay>> = MutableLiveData()
    val weatherData: LiveData<List<WeatherPerDay>> = _data

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    @MainThread
    suspend fun loadTasks(
        lat: Double,
        lon: Double,
        cnt: Int = 40,
        units: Units = Units.metric
    ) {
        try {
            val loadedList = withContext(Dispatchers.IO) {
                dataSource.loadWeatherByLoc(lat, lon, cnt, units).list
            }
            val resList : List<WeatherPerDay> = emptyList()
            for ()
        } catch (e: Exception) {
            _error.postValue(e)
        }
    }

    fun dataParse
}