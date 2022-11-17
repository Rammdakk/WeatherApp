package com.rammdakk.avitotestproj.data.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rammdakk.avitotestproj.data.datasource.DataSource
import com.rammdakk.avitotestproj.data.model.CityInfo
import com.rammdakk.avitotestproj.data.model.WeatherPerDay
import com.rammdakk.avitotestproj.data.restApi.Units
import com.rammdakk.todo.ioc.ApplicationComponentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@ApplicationComponentScope
class WeatherRepository @Inject constructor(
    private val dataSource: DataSource
) {
    private val _weather: MutableLiveData<List<WeatherPerDay>> = MutableLiveData()
    val weatherData: LiveData<List<WeatherPerDay>> = _weather

    private val _cityInfo: MutableLiveData<CityInfo> = MutableLiveData()
    val cityInfo: LiveData<CityInfo> = _cityInfo

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    @MainThread
    suspend fun loadWeather(
        cnt: Int = 40,
        units: Units = Units.metric,
        lat: Double = 0.0,
        lon: Double = 0.0,
        cityName: String?
    ) {
        try {
            val loadedList = if (cityName != null) {
                withContext(Dispatchers.IO) {
                    dataSource.loadWeatherByCityName(cityName, cnt, units)
                }
            } else {
                withContext(Dispatchers.IO) {
                    dataSource.loadWeatherByLoc(lat, lon, cnt, units)
                }
            }
            val resList: HashMap<String, WeatherPerDay> = HashMap()
            for (item in loadedList.list) {
                val date = java.sql.Date(item.dateTime * 1000)
                var data = resList.putIfAbsent(date.toString(), WeatherPerDay(date = date))
                if (data == null) {
                    data = resList.get(date.toString())
                }
                val calendar = GregorianCalendar.getInstance()
                calendar.time = Date(item.dateTime * 1000)
                when (calendar.get(Calendar.HOUR_OF_DAY)) {
                    3 -> data?.nightTemp = item.temp.temperature
                    9 -> data?.morningTemp = item.temp.temperature
                    15 -> data?.dayTemp = item.temp.temperature
                    21 -> data?.evTemp = item.temp.temperature
                }
            }
            _cityInfo.postValue(
                CityInfo(
                    loadedList.city.name, loadedList.list[0].temp.temperature, cityName == null
                )
            )
            _weather.postValue(ArrayList(resList.values).sortedBy { it.date })
        } catch (e: Exception) {
            _error.postValue(e)
        }
    }


}