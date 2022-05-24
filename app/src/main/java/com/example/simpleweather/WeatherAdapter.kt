package com.example.simpleweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class WeatherAdapter(val mainList : ArrayList<qwerasdf>,
    val layoutInflater: LayoutInflater)
    : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val cloud : TextView
        val wind : TextView
        val weather : TextView
        val time : TextView
        val icon : TextView
        val main : TextView
        val description : TextView

        init {
            cloud = itemView.findViewById(R.id.cloud)
            wind = itemView.findViewById(R.id.wind)
            weather = itemView.findViewById(R.id.weather)
            time = itemView.findViewById(R.id.time)
            icon = itemView.findViewById(R.id.icon)
            main = itemView.findViewById(R.id.main)
            description = itemView.findViewById(R.id.description)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHolder {
        val view = layoutInflater.inflate(R.layout.weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {
        holder.cloud.text = mainList.get(position).clouds.all.toString()
        holder.wind.text = mainList.get(position).wind.speed.toString()
        holder.weather.text = (mainList.get(position).main.temp-273.15).roundToInt().toString()
        holder.time.text = mainList.get(position).dt_txt
        holder.icon.text = mainList.get(position).weather.firstOrNull()?.icon
        holder.main.text = mainList.get(position).weather.firstOrNull()?.main
        holder.description.text = mainList.get(position).weather.firstOrNull()?.description

    }

    override fun getItemCount(): Int {
        return mainList.size
    }

}