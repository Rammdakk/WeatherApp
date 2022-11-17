package com.rammdakk.avitotestproj.ui.view.weatherFragment

import androidx.recyclerview.widget.RecyclerView
import com.rammdakk.avitotestproj.data.model.WeatherPerDay
import com.rammdakk.avitotestproj.databinding.WeekForecastCellBinding

class WeatherViewHolder(
    private val weekForecastCellBinding: WeekForecastCellBinding
) : RecyclerView.ViewHolder(weekForecastCellBinding.root) {

    fun bind(weatherPerDay: WeatherPerDay) {
        weekForecastCellBinding.dateTV.text = weatherPerDay.date.toString()
        weekForecastCellBinding.morningNumberTextView.text =
            if (weatherPerDay.morningTemp != null) "${weatherPerDay.morningTemp}°" else "-"
        weekForecastCellBinding.dayNumberTextView.text =
            if (weatherPerDay.dayTemp != null) "${weatherPerDay.dayTemp}°" else "-"
        weekForecastCellBinding.eveningNumberTextView.text =
            if (weatherPerDay.evTemp != null) "${weatherPerDay.evTemp}°" else "-"
        weekForecastCellBinding.nightNumberTextView.text =
            if (weatherPerDay.nightTemp != null) "${weatherPerDay.nightTemp}°" else "-"
    }
}