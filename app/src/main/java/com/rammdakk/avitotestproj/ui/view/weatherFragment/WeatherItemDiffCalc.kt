package com.rammdakk.avitotestproj.ui.view.weatherFragment

import androidx.recyclerview.widget.DiffUtil
import com.rammdakk.avitotestproj.data.WeatherPerDay

class WeatherItemDiffCalc : DiffUtil.ItemCallback<WeatherPerDay>() {
    override fun areItemsTheSame(oldItem: WeatherPerDay, newItem: WeatherPerDay): Boolean {
       return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: WeatherPerDay, newItem: WeatherPerDay): Boolean {
        return oldItem == newItem
    }
}