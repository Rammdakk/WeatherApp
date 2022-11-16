package com.rammdakk.avitotestproj.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

data class WeatherPerDay(
    @SerializedName("date")
    val date: Date = Date(System.currentTimeMillis()),
    @SerializedName("morningTemp")
    val morningTemp: Double? = null,
    @SerializedName("dayTemp")
    val dayTemp: Double? = null,
    @SerializedName("evTemp")
    val evTemp: Double? = null,
    @SerializedName("nightTemp")
    val nightTemp: Double? = null,
)
