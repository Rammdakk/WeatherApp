package com.rammdakk.avitotestproj.ui.view.weatherFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rammdakk.avitotestproj.databinding.FragmentWeatherBinding
import com.rammdakk.avitotestproj.ioc.WeatherFragmentComponent
import com.rammdakk.avitotestproj.ioc.WeatherFragmentViewComponent
import com.rammdakk.avitotestproj.ui.stateholders.WeatherViewModel

class WeatherFragment : Fragment() {
    private lateinit var fragmentComponent: WeatherFragmentComponent
    private var fragmentViewComponent: WeatherFragmentViewComponent? = null
    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        fragmentComponent = WeatherFragmentComponent(fragment = this, viewModel = viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherBinding.inflate(inflater, container, false);
        val view = binding.root
        fragmentViewComponent = WeatherFragmentViewComponent(
            fragmentComponent = fragmentComponent,
            root = view,
            binding = binding,
            lifecycleOwner = viewLifecycleOwner
        ).apply {
            weatherController.setUpViews()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
    }
}