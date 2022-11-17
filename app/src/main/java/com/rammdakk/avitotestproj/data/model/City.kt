package com.rammdakk.avitotestproj.data.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("name")
    val name: String
)

data class CityInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("name")
    val curentTemp: Double
)