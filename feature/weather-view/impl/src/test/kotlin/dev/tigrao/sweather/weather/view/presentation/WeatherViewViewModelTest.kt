package dev.tigrao.sweather.weather.view.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.infra.binds.NativeStringType
import dev.tigrao.sweather.weather.view.R
import dev.tigrao.sweather.weather.view.domain.FetchWeatherDataByGeoLocationUseCase
import dev.tigrao.sweather.weather.view.domain.model.WeatherLocationErrorModel
import dev.tigrao.sweather.weather.view.domain.model.WeatherLocationModel
import dev.tigrao.sweather.weather.view.presentation.model.ErrorMessageVO
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewAction
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewState
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewVO
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewViewModelTest {

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    private val mapFromWeatherModelToVO = mockk<MapFromWeatherModelToVO>()
    private val fetchWeatherDataByGeoLocationUseCase = mockk<FetchWeatherDataByGeoLocationUseCase>()
    private val viewState = WeatherViewState()

    private lateinit var subject: WeatherViewViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun init_postErrorNotVisible() {
        prepareScenario()

        assertEquals(
            ErrorMessageVO<WeatherViewAction>(false),
            viewState.showError.value
        )
    }

    @Test
    fun init_postLayoutNotVisible() {
        prepareScenario()

        assertFalse(viewState.showLayout.value!!)
    }

    @Test
    fun init_postLoadingVisible() {
        prepareScenario()

        assertTrue(viewState.showLoading.value!!)
    }

    @Test
    fun dispatch_tryAgainSuccess_verifyViewState() {
        val weatherViewVO = mockk<WeatherViewVO>()

        val loading = mockk<Observer<Boolean>>(relaxed = true)
        val layout = mockk<Observer<Boolean>>(relaxed = true)
        val error = mockk<Observer<ErrorMessageVO<WeatherViewAction>>>(relaxed = true)
        val viewVO = mockk<Observer<WeatherViewVO>>(relaxed = true)
        viewState.showLoading.observeForever(loading)
        viewState.showError.observeForever(error)
        viewState.showLayout.observeForever(layout)
        viewState.weatherView.observeForever(viewVO)

        prepareScenario(
            fetch = Result.Success(mockk()),
            weatherViewVO = weatherViewVO
        )

        subject.dispatch(WeatherViewAction.TryAgain)

        verifySequence {
            // Need to check twice because of init
            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(false)
            layout.onChanged(true)
            viewVO.onChanged(weatherViewVO)
        }
    }

    @Test
    fun dispatch_tryAgainError_verifyViewState() {
        val weatherViewVO = mockk<WeatherViewVO>()

        val loading = mockk<Observer<Boolean>>(relaxed = true)
        val layout = mockk<Observer<Boolean>>(relaxed = true)
        val error = mockk<Observer<ErrorMessageVO<WeatherViewAction>>>(relaxed = true)
        val viewVO = mockk<Observer<WeatherViewVO>>(relaxed = true)
        viewState.showLoading.observeForever(loading)
        viewState.showError.observeForever(error)
        viewState.showLayout.observeForever(layout)
        viewState.weatherView.observeForever(viewVO)

        prepareScenario(
            fetch = Result.Error(mockk()),
            weatherViewVO = weatherViewVO
        )

        subject.dispatch(WeatherViewAction.TryAgain)

        verifySequence {
            // Need to check twice because of init
            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(false)
            error.onChanged(
                ErrorMessageVO(
                    visible = true,
                    nativeStringType = NativeStringType(
                        res = R.string.generic_error_message,
                    ),
                    WeatherViewAction.TryAgain,
                )
            )
        }
    }

    @Test
    fun dispatch_checkPermission_verifyViewState() {
        val weatherViewVO = mockk<WeatherViewVO>()

        val loading = mockk<Observer<Boolean>>(relaxed = true)
        val layout = mockk<Observer<Boolean>>(relaxed = true)
        val error = mockk<Observer<ErrorMessageVO<WeatherViewAction>>>(relaxed = true)
        val checkPermission = mockk<Observer<Unit>>(relaxed = true)
        viewState.showLoading.observeForever(loading)
        viewState.showError.observeForever(error)
        viewState.showLayout.observeForever(layout)
        viewState.checkPermission.observeForever(checkPermission)

        prepareScenario(
            fetch = Result.Error(mockk()),
            weatherViewVO = weatherViewVO
        )

        subject.dispatch(WeatherViewAction.CheckPermission)

        verifySequence {
            // Need to check twice because of init
            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            checkPermission.onChanged(null)
        }
    }

    @Test
    fun dispatch_PermissionGrantedSuccess_verifyViewState() {
        val weatherViewVO = mockk<WeatherViewVO>()

        val loading = mockk<Observer<Boolean>>(relaxed = true)
        val layout = mockk<Observer<Boolean>>(relaxed = true)
        val error = mockk<Observer<ErrorMessageVO<WeatherViewAction>>>(relaxed = true)
        val viewVO = mockk<Observer<WeatherViewVO>>(relaxed = true)
        viewState.showLoading.observeForever(loading)
        viewState.showError.observeForever(error)
        viewState.showLayout.observeForever(layout)
        viewState.weatherView.observeForever(viewVO)

        prepareScenario(
            fetch = Result.Success(mockk()),
            weatherViewVO = weatherViewVO
        )

        subject.dispatch(WeatherViewAction.PermissionGranted)

        verifySequence {
            // Need to check twice because of init
            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(false)
            layout.onChanged(true)
            viewVO.onChanged(weatherViewVO)
        }
    }

    @Test
    fun dispatch_PermissionGrantedError_verifyViewState() {
        val weatherViewVO = mockk<WeatherViewVO>()

        val loading = mockk<Observer<Boolean>>(relaxed = true)
        val layout = mockk<Observer<Boolean>>(relaxed = true)
        val error = mockk<Observer<ErrorMessageVO<WeatherViewAction>>>(relaxed = true)
        val viewVO = mockk<Observer<WeatherViewVO>>(relaxed = true)
        viewState.showLoading.observeForever(loading)
        viewState.showError.observeForever(error)
        viewState.showLayout.observeForever(layout)
        viewState.weatherView.observeForever(viewVO)

        prepareScenario(
            fetch = Result.Error(mockk()),
            weatherViewVO = weatherViewVO
        )

        subject.dispatch(WeatherViewAction.PermissionGranted)

        verifySequence {
            // Need to check twice because of init
            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(false)
            error.onChanged(
                ErrorMessageVO(
                    visible = true,
                    nativeStringType = NativeStringType(
                        res = R.string.generic_error_message,
                    ),
                    WeatherViewAction.TryAgain,
                )
            )
        }
    }

    @Test
    fun dispatch_PermissionNotGranted_verifyViewState() {
        val weatherViewVO = mockk<WeatherViewVO>()

        val loading = mockk<Observer<Boolean>>(relaxed = true)
        val layout = mockk<Observer<Boolean>>(relaxed = true)
        val error = mockk<Observer<ErrorMessageVO<WeatherViewAction>>>(relaxed = true)
        val viewVO = mockk<Observer<WeatherViewVO>>(relaxed = true)
        viewState.showLoading.observeForever(loading)
        viewState.showError.observeForever(error)
        viewState.showLayout.observeForever(layout)
        viewState.weatherView.observeForever(viewVO)

        prepareScenario(
            fetch = Result.Error(mockk()),
            weatherViewVO = weatherViewVO
        )

        subject.dispatch(WeatherViewAction.PermissionNotGranted)

        verifySequence {
            // Need to check twice because of init
            loading.onChanged(true)
            layout.onChanged(false)
            error.onChanged(ErrorMessageVO(visible = false))

            loading.onChanged(false)
            error.onChanged(
                ErrorMessageVO(
                    visible = true,
                    nativeStringType = NativeStringType(
                        res = R.string.permission_not_granted_error,
                    ),
                    WeatherViewAction.CheckPermission,
                )
            )
        }
    }

    private fun prepareScenario(
        fetch: Result<WeatherLocationModel, WeatherLocationErrorModel> = Result.Error(mockk()),
        weatherViewVO: WeatherViewVO = mockk(),
    ) {
        coEvery { fetchWeatherDataByGeoLocationUseCase() } returns fetch
        every { mapFromWeatherModelToVO.mapFrom(any()) } returns weatherViewVO

        subject = WeatherViewViewModel(
            viewState,
            fetchWeatherDataByGeoLocationUseCase,
            mapFromWeatherModelToVO,
        )
    }
}
