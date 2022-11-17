package com.rammdakk.avitotestproj.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherPerDay(
    @SerializedName("date")
    val date: Date = Date(System.currentTimeMillis()),
    @SerializedName("morningTemp")
    var morningTemp: Double? = null,
    @SerializedName("dayTemp")
    var dayTemp: Double? = null,
    @SerializedName("evTemp")
    var evTemp: Double? = null,
    @SerializedName("nightTemp")
    var nightTemp: Double? = null,
)
