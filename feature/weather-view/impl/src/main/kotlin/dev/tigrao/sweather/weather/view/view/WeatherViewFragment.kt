package dev.tigrao.sweather.weather.view.view

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import dev.tigrao.sweather.infra.common.resource.getAttrDimen
import dev.tigrao.sweather.weather.view.R
import dev.tigrao.sweather.weather.view.databinding.FragmentWeatherViewBinding
import dev.tigrao.sweather.weather.view.presentation.WeatherViewViewModel
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewAction
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
        locationPermission()
        prepareWeatherConditionList()
    }

    private fun prepareObserver() {
        with(viewModel.viewState) {
            this.weatherView.observe(viewLifecycleOwner) {
                Glide
                    .with(requireContext())
                    .load(it.condition.icon)
                    .into(viewBinding.imgCondition)

                viewBinding.rvWeatherInfo.adapter = WeatherInfoAdapter(it.weatherItemList)
            }

            this.showError.observe(viewLifecycleOwner) { errorVO ->
                viewBinding.btnTryAgain.setOnClickListener {
                    errorVO.action?.invoke()
                }
            }

            this.checkPermission.observe(viewLifecycleOwner) {
                locationPermission()
            }
        }
    }

    private fun prepareWeatherConditionList() {
        with(viewBinding.rvWeatherInfo) {
            layoutManager = GridLayoutManager(
                requireContext(),
                3,
            )

            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)

                    outRect.top = parent.context.getAttrDimen(R.attr.spacingSM, R.style.Theme_MyApp)
                }
            })
        }
    }

    private fun locationPermission() {
        Dexter.withContext(requireContext())
            .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    viewModel.dispatch(WeatherViewAction.PermissionGranted)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    viewModel.dispatch(WeatherViewAction.PermissionNotGranted)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    DialogOnDeniedPermissionListener.Builder
                        .withContext(context)
                        .withTitle("Camera permission")
                        .withMessage("Camera permission is needed to take pictures of your cat")
                        .withButtonText(android.R.string.ok)
                        .build()
                        .onPermissionRationaleShouldBeShown(p0, p1)
                }

            }).check()
    }
}
