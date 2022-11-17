package com.rammdakk.avitotestproj.data.model

import com.google.gson.annotations.SerializedName

data class WeatherToDate(
    @SerializedName("dt")
    var dateTime: Long = System.currentTimeMillis() / 1000,
    @SerializedName("main")
    var temp: Temperature
)

data class Temperature(
    @SerializedName("temp")
    val temperature: Double = 0.0
)

