package ru.garshishka.timeforatrip.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.garshishka.timeforatrip.R
import ru.garshishka.timeforatrip.databinding.LayoutFlightBinding
import ru.garshishka.timeforatrip.dto.Flight
import ru.garshishka.timeforatrip.utils.FlightInteractionListener
import ru.garshishka.timeforatrip.utils.getTimeGlobal

class FlightViewHolder(
    private val binding: LayoutFlightBinding,
    private val flightInteractionListener: FlightInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(flight: Flight) {
        binding.apply {
            cityText.text = "${flight.startCity} - ${flight.endCity}"
            startDate.text = getTimeGlobal(flight.startDate)
            endDate.text = getTimeGlobal(flight.endDate)
            priceText.text = "${flight.price} ₽"
            if (flight.likedByMe) {
                likeIcon.setImageResource(R.drawable.favorite_48)
            }
            fligtBody.setOnClickListener {
                flightInteractionListener.onFlightClick(flight)
            }
        }
    }
}

class FlightsAdapter(private val flightInteractionListener: FlightInteractionListener) : ListAdapter<Flight, FlightViewHolder>(FlightDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding =
            LayoutFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(binding, flightInteractionListener)
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