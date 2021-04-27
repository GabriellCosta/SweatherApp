package dev.tigrao.sweather.weather.view.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.tigrao.sweather.weather.view.presentation.model.WeatherItemVO

internal class WeatherInfoAdapter(
    private val list: List<WeatherItemVO>
) : RecyclerView.Adapter<WeatherInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoViewHolder {
        return WeatherInfoViewHolder(parent.context)
    }

    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        val current = list[position]

        holder.bind(current)
    }

    override fun getItemCount(): Int = list.size
}
