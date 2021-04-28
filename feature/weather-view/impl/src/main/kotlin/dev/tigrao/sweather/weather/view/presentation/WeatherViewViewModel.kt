package dev.tigrao.sweather.weather.view.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tigrao.sweather.infra.binds.NativeStringType
import dev.tigrao.sweather.weather.view.R
import dev.tigrao.sweather.weather.view.domain.FetchWeatherDataByGeoLocationUseCase
import dev.tigrao.sweather.weather.view.presentation.model.ErrorMessageVO
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewAction
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewState
import kotlinx.coroutines.launch

internal class WeatherViewViewModel(
    private val fetchWeatherDataByGeoLocationUseCase: FetchWeatherDataByGeoLocationUseCase,
    private val mapFromWeatherModelToVO: MapFromWeatherModelToVO,
) : ViewModel() {

    val viewState: WeatherViewState = WeatherViewState()

    init {
        initState()
    }

    fun dispatch(action: WeatherViewAction) =
        when (action) {
            WeatherViewAction.PermissionGranted -> onPermissionGranted()
            WeatherViewAction.PermissionNotGranted -> onPermissionNotGranted()
            WeatherViewAction.TryAgain -> fetch()
            WeatherViewAction.CheckPermission -> checkPermission()
        }

    private fun checkPermission() {
        viewState.checkPermission.call()
    }

    private fun onPermissionNotGranted() {
        viewState.showLoading.postValue(false)
        viewState.showError.postValue(
            ErrorMessageVO(
                visible = true,
                nativeStringType = NativeStringType(
                    res = R.string.permission_not_granted_error
                ),
                WeatherViewAction.CheckPermission
            )
        )
    }

    private fun onPermissionGranted() {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            initState()

            fetchWeatherDataByGeoLocationUseCase()
                .onSuccess {
                    viewState.showLoading.postValue(false)
                    viewState.showLayout.postValue(true)

                    viewState.weatherView.postValue(mapFromWeatherModelToVO.mapFrom(it))
                }
                .onError {
                    viewState.showLoading.postValue(false)
                    viewState.showError.postValue(
                        ErrorMessageVO(
                            visible = true,
                            nativeStringType = NativeStringType(
                                res = R.string.permission_not_granted_error
                            ),
                            WeatherViewAction.TryAgain,
                        )
                    )
                }
        }
    }

    private fun initState() {
        viewState.showLoading.postValue(true)
        viewState.showLayout.postValue(false)
        viewState.showError.postValue(
            ErrorMessageVO(
                visible = false,
                nativeStringType = NativeStringType(
                    res = R.string.permission_not_granted_error
                ),
                WeatherViewAction.TryAgain,
            )
        )
    }
}
