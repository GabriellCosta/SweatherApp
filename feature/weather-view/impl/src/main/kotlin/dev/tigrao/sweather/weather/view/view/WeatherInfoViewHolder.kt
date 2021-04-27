package dev.tigrao.sweather.weather.view.view

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.tigrao.sweather.weather.view.databinding.IncludeWeatherItemBinding
import dev.tigrao.sweather.weather.view.presentation.model.WeatherItemVO

internal class WeatherInfoViewHolder(itemView: IncludeWeatherItemBinding) :
    RecyclerView.ViewHolder(itemView.root) {

    companion object {
        operator fun invoke(context: Context): WeatherInfoViewHolder {
            val binding = IncludeWeatherItemBinding.inflate(LayoutInflater.from(context))

            return WeatherInfoViewHolder(binding)
        }
    }

    private val viewBinder by viewBinding { itemView }

    fun bind(item: WeatherItemVO) {
        viewBinder.content = item
    }
}
