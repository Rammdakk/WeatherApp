package com.rammdakk.avitotestproj.data.restApi

import com.rammdakk.avitotestproj.data.model.City
import com.rammdakk.avitotestproj.data.model.WeatherToDate


class ListOfWeatherToDate(
    val city: City,
    val list: List<WeatherToDate>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ListOfWeatherToDate
        if (list != other.list) return false
        return true
    }

    override fun hashCode(): Int {
        return list.hashCode()
    }
}

