package dev.tigrao.sweather.weather.view.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dev.tigrao.sweather.weather.view.R
import dev.tigrao.sweather.weather.view.databinding.FragmentWeatherViewBinding
import dev.tigrao.sweather.weather.view.presentation.WeatherViewViewModel
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class WeatherViewFragment : Fragment(R.layout.fragment_weather_view) {

    private val viewModel by viewModel<WeatherViewViewModel>()
    private val viewBinding by viewBinding(FragmentWeatherViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {
            viewState = viewModel.viewState
            lifecycleOwner = viewLifecycleOwner
        }

        prepareObserver()
    }

    private fun prepareObserver() {
        with(viewModel.viewState) {
            this.weatherView.observe(viewLifecycleOwner) {
                Glide.with(viewBinding.imgCondition)
                    .load(it.condition.icon.toString())
                    .into(viewBinding.imgCondition)
            }
        }
    }
}
