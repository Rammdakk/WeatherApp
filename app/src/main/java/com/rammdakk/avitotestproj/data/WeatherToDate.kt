package com.rammdakk.avitotestproj.data

import com.google.gson.annotations.SerializedName

data class WeatherToDate(
    @SerializedName("dt")
    var dateTime: Long = System.currentTimeMillis() / 1000,
    var main: Temperature
)

data class Temperature(
    @SerializedName("temp")
    val temperature: Double = 0.0
)

