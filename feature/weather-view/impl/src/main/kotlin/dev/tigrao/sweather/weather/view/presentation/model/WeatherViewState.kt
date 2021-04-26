package dev.tigrao.sweather.weather.view.presentation.model

import android.view.View
import androidx.lifecycle.MutableLiveData

internal class WeatherViewState {

    val weatherView = MutableLiveData<WeatherViewVO>()

    val showLoading = MutableLiveData<Int>()
    val showError = MutableLiveData(View.GONE)
    val showLayout = MutableLiveData<Int>()
}
