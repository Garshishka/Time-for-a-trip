package ru.garshishka.timeforatrip.utils

import ru.garshishka.timeforatrip.dto.Flight

interface FlightInteractionListener {
    fun onFlightClick(flight: Flight)
}