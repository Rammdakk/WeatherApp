package com.rammdakk.avitotestproj.data.datasource

import android.util.Log
import com.rammdakk.avitotestproj.data.restApi.HTTPResponseCodeException
import com.rammdakk.avitotestproj.data.restApi.JsonPlaceHolderApi
import com.rammdakk.avitotestproj.data.restApi.ListOfWeatherToDate
import com.rammdakk.avitotestproj.data.restApi.Units
import com.rammdakk.todo.ioc.ApplicationComponentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@ApplicationComponentScope
class DataSource @Inject constructor(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi,
) {

    private val ACCESS_KEY = "7cd5c37626cce6c6d36726c58b931504"

    suspend fun loadWeatherByLoc(
        lat: Double,
        lon: Double,
        cnt: Int = 40,
        units: Units = Units.metric,
    ): ListOfWeatherToDate {
        val response = jsonPlaceHolderApi.getWeatherByLoc(
            key = ACCESS_KEY,
            locLat = lat,
            locLon = lon,
            numberOfAnswers = cnt,
            unit = units
        )
        var listOfWeatherData: ListOfWeatherToDate
        withContext(Dispatchers.Main) {
            try {
                if (response.isSuccessful) {
                    listOfWeatherData = response.body()!!
                } else {
                    throw HTTPResponseCodeException(response.code().toString())
                }
            } catch (e: HttpException) {
                Log.d(e.code().toString(), e.message())
                throw e
                // Toast.makeText("Exception ${e.message}")
            }
        }
        return listOfWeatherData
    }

    suspend fun loadWeatherByCityName(
        cityName: String,
        cnt: Int = 40,
        units: Units = Units.metric,
    ): ListOfWeatherToDate {
        val response = jsonPlaceHolderApi.getWeatherByCityName(
            key = ACCESS_KEY,
            cityName = cityName,
            numberOfAnswers = cnt,
            unit = units
        )
        var listOfWeatherData: ListOfWeatherToDate
        withContext(Dispatchers.Main) {
            try {
                if (response.isSuccessful) {
                    listOfWeatherData = response.body()!!
                } else {
                    throw HTTPResponseCodeException(response.message().toString())
                }
            } catch (e: HttpException) {
                Log.d(e.code().toString(), e.message())
                // Toast.makeText("Exception ${e.message}")
                throw HTTPResponseCodeException(e.message().toString())
            }
        }
        return listOfWeatherData
    }
}