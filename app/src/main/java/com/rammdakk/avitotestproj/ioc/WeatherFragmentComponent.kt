package com.rammdakk.avitotestproj.ioc

import androidx.fragment.app.Fragment
import com.rammdakk.avitotestproj.ui.stateholders.WeatherViewModel
import com.rammdakk.avitotestproj.ui.view.weatherFragment.WeatherAdapter
import com.rammdakk.avitotestproj.ui.view.weatherFragment.WeatherFragment
import com.rammdakk.avitotestproj.ui.view.weatherFragment.WeatherItemDiffCalc

class WeatherFragmentComponent(
    val fragment: Fragment,
    val viewModel: WeatherViewModel
) {
    val adapter = WeatherAdapter(WeatherItemDiffCalc())
}