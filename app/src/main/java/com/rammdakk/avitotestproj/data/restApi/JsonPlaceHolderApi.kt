package com.rammdakk.avitotestproj.data.restApi

import retrofit2.Response
import retrofit2.http.*

interface JsonPlaceHolderApi {

    @GET("forecast?appid={key}")
    suspend fun getWeatherByLoc(
        @Path("key") key: String,
        @Query("lat") locLat: Double,
        @Query("lon") locLon: Double,
        @Query("cnt") numberOfAnswers: Int,
        @Query("units") unit: Units
    ): Response<ListOfWeatherToDate>

    @GET("forecast")
    suspend fun getWeatherByCityName(
        @Query("appid") key: String,
        @Query("q") cityName: String,
        @Query("cnt") numberOfAnswers: Int,
        @Query("units") unit: Units
    ): Response<ListOfWeatherToDate>

}