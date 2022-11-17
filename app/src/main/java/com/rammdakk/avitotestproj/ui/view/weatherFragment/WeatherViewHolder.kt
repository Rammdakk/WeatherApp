package com.rammdakk.avitotestproj.ui.view.weatherFragment

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.rammdakk.avitotestproj.R
import com.rammdakk.avitotestproj.data.model.WeatherPerDay
import com.rammdakk.avitotestproj.databinding.WeekForecastCellBinding
import java.text.SimpleDateFormat

class WeatherViewHolder(
    private val weekForecastCellBinding: WeekForecastCellBinding
) : RecyclerView.ViewHolder(weekForecastCellBinding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(weatherPerDay: WeatherPerDay) {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val context = weekForecastCellBinding.root.context
        weekForecastCellBinding.dateTV.text = formatter.format(weatherPerDay.date)
        weekForecastCellBinding.morningNumberTextView.text =
            if (weatherPerDay.morningTemp != null) context.getString(R.string.temper_string, weatherPerDay.morningTemp) else "-"
        weekForecastCellBinding.dayNumberTextView.text =
            if (weatherPerDay.dayTemp != null) context.getString(R.string.temper_string, weatherPerDay.dayTemp) else "-"
        weekForecastCellBinding.eveningNumberTextView.text =
            if (weatherPerDay.evTemp != null) context.getString(R.string.temper_string, weatherPerDay.evTemp) else "-"
        weekForecastCellBinding.nightNumberTextView.text =
            if (weatherPerDay.nightTemp != null) context.getString(R.string.temper_string, weatherPerDay.nightTemp) else "-"
    }
}