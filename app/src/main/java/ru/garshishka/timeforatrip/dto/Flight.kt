package ru.garshishka.timeforatrip.dto

import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("startLocationCode")
    val startLocationCode: String,
    @SerializedName("endLocationCode")
    val endLocationCode: String,
    @SerializedName("startCity")
    val startCity: String,
    @SerializedName("endCity")
    val endCity: String,
    @SerializedName("serviceClass")
    val serviceClass: String,
    @SerializedName("seats")
    val seats: List<Seat>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("searchToken")
    val searchToken: String,
)

data class Flights(
    @SerializedName("flights")
    val flights: List<Flight>
)

