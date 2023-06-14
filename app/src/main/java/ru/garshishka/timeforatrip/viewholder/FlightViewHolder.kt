package ru.garshishka.timeforatrip.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.garshishka.timeforatrip.databinding.LayoutFlightBinding
import ru.garshishka.timeforatrip.dto.Flight
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FlightViewHolder(
    private val binding: LayoutFlightBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(flight: Flight) {
        binding.apply {
            cityText.text = "${flight.startCity} - ${flight.endCity}"

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z z")
            val formatterNew = DateTimeFormatter.ofPattern("HH:mm:ss yyyy.MM.dd Z z")
            val startDateTime = ZonedDateTime.parse(flight.startDate, formatter)
            val endDateTime = ZonedDateTime.parse(flight.endDate, formatter)
            startDate.text = startDateTime.format(formatterNew)
            endDate.text = endDateTime.format(formatterNew)
            priceText.text = "${flight.price} руб."
            likeButton.isChecked = flight.likedByMe
            likeButton.setOnClickListener {
                likeButton.isChecked = !likeButton.isChecked
            }
        }
    }
}

class FlightsAdapter() : ListAdapter<Flight, FlightViewHolder>(FlightDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding = LayoutFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = getItem(position) ?: return
        holder.bind(flight)
    }

}

class FlightDiffCallback : DiffUtil.ItemCallback<Flight>() {
    override fun areItemsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem.searchToken == newItem.searchToken
    }

    override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem == newItem
    }

}