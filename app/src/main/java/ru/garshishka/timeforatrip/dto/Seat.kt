package ru.garshishka.timeforatrip.dto

import com.google.gson.annotations.SerializedName


data class Seat(
    @SerializedName("passengerType")
    val passengerType : PassengerType,
    @SerializedName("count")
    val count : Int,
)

enum class PassengerType{
    CHD,
    INF,
    ADT
}