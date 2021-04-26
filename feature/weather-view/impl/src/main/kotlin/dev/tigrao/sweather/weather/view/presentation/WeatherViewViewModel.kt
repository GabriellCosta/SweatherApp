package dev.tigrao.sweather.weather.view.presentation

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tigrao.sweather.weather.view.domain.FetchWeatherDataByGeoLocationUseCase
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewAction
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewState
import kotlinx.coroutines.launch

internal class WeatherViewViewModel(
    private val fetchWeatherDataByGeoLocationUseCase: FetchWeatherDataByGeoLocationUseCase,
    private val mapFromWeatherModelToVO: MapFromWeatherModelToVO,
) : ViewModel() {

    val viewState: WeatherViewState = WeatherViewState()

    fun dispatch(action: WeatherViewAction) =
        when (action) {
            WeatherViewAction.PermissionGranted -> onPermissionGranted()
            WeatherViewAction.PermissionNotGranted -> onPermissionNotGranted()
            WeatherViewAction.TryAgain -> fetch()
        }

    private fun onPermissionNotGranted() {

    }

    private fun onPermissionGranted() {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            viewState.showLoading.postValue(
                View.VISIBLE
            )
            viewState.showLayout.postValue(
                View.GONE
            )

            viewState.showError.postValue(
                View.GONE
            )

            fetchWeatherDataByGeoLocationUseCase()
                .onSuccess {
                    viewState.showLoading.postValue(
                        View.GONE
                    )
                    viewState.showLayout.postValue(
                        View.VISIBLE
                    )

                    viewState.weatherView.postValue(
                        mapFromWeatherModelToVO.mapFrom(it)
                    )
                }
                .onError {
                    viewState.showLoading.postValue(
                        View.GONE
                    )
                    viewState.showError.postValue(
                        View.VISIBLE
                    )
                }
        }
    }
}
