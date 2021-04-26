package dev.tigrao.sweather.weather.view.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tigrao.sweather.weather.view.domain.FetchWeatherDataByGeoLocationUseCase
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewState
import kotlinx.coroutines.launch

internal class WeatherViewViewModel(
    private val fetchWeatherDataByGeoLocationUseCase: FetchWeatherDataByGeoLocationUseCase,
    private val mapFromWeatherModelToVO: MapFromWeatherModelToVO,
) : ViewModel() {

    val viewState: WeatherViewState = WeatherViewState()

    init {
        viewModelScope.launch {
            fetchWeatherDataByGeoLocationUseCase()
                .onSuccess {
                    viewState.weatherView.postValue(
                        mapFromWeatherModelToVO.mapFrom(it)
                    )
                }
                .onError {

                }
        }
    }
}
