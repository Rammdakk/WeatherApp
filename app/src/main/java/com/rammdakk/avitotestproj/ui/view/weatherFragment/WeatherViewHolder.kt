package com.rammdakk.avitotestproj.ui.view.weatherFragment

import androidx.recyclerview.widget.RecyclerView
import com.rammdakk.avitotestproj.data.model.WeatherPerDay
import com.rammdakk.avitotestproj.databinding.WeekForecastCellBinding
import java.text.DateFormat
import java.text.SimpleDateFormat

class WeatherViewHolder(
    private val weekForecastCellBinding: WeekForecastCellBinding
) : RecyclerView.ViewHolder(weekForecastCellBinding.root) {

    fun bind(weatherPerDay: WeatherPerDay) {
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        weekForecastCellBinding.dateTV.text = formatter.format(weatherPerDay.date)
        weekForecastCellBinding.morningNumberTextView.text = "${weatherPerDay.morningTemp}°"
        weekForecastCellBinding.dayNumberTextView.text = "${weatherPerDay.dayTemp}°"
        weekForecastCellBinding.eveningNumberTextView.text = "${weatherPerDay.evTemp}°"
        weekForecastCellBinding.nightNumberTextView.text = "${weatherPerDay.nightTemp}°"
    }
}