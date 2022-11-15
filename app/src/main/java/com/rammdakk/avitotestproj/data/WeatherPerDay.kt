package com.rammdakk.avitotestproj.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

data class WeatherPerDay(
    @SerializedName("date")
    val date: Date = Date(System.currentTimeMillis()),
    @SerializedName("morningTemp")
    val morningTemp : Double = 0.0,
    @SerializedName("dayTemp")
    val dayTemp : Double = 0.0,
    @SerializedName("evTemp")
    val evTemp : Double = 0.0,
    @SerializedName("nightTemp")
    val nightTemp : Double = 0.0,
)
