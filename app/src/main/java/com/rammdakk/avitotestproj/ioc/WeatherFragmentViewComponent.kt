package com.rammdakk.avitotestproj.ioc

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.rammdakk.avitotestproj.databinding.FragmentWeatherBinding
import com.rammdakk.avitotestproj.ui.view.weatherFragment.WeatherController

class WeatherFragmentViewComponent(
    fragmentComponent: WeatherFragmentComponent,
    root: View,
    binding: FragmentWeatherBinding,
    lifecycleOwner: LifecycleOwner,
) {
    val weatherController = WeatherController(
        fragmentComponent.fragment.requireActivity(),
        root,
        binding,
        fragmentComponent.adapter,
        lifecycleOwner,
        fragmentComponent.viewModel,
    )
}