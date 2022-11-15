package com.rammdakk.avitotestproj.ui.view.weatherFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rammdakk.avitotestproj.R
import com.rammdakk.avitotestproj.data.WeatherPerDay
import com.rammdakk.avitotestproj.databinding.WeekForecastCellBinding
import com.rammdakk.avitotestproj.ui.stateholders.WeatherViewModel

class WeatherAdapter(
    taskItemDiffCalc: WeatherItemDiffCalc,
) : ListAdapter<WeatherPerDay, WeatherViewHolder>(taskItemDiffCalc) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val weekForecastCellBinding = WeekForecastCellBinding.inflate(layoutInflater, parent, false)
        return WeatherViewHolder(weekForecastCellBinding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}