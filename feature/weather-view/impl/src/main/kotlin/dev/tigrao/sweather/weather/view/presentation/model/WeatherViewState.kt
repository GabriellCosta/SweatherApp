package dev.tigrao.sweather.weather.view.presentation.model

import androidx.lifecycle.MutableLiveData
import dev.tigrao.sweather.infra.common.livedata.SingleEventLiveData

internal class WeatherViewState {

    val weatherView = MutableLiveData<WeatherViewVO>()

    val showLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<ErrorMessageVO<WeatherViewAction>>()
    val showLayout = MutableLiveData<Boolean>()

    val checkPermission = SingleEventLiveData<Unit>()
}
