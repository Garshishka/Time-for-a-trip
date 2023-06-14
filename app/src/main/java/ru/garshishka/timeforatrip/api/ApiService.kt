package ru.garshishka.timeforatrip

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.garshishka.timeforatrip.dto.Flights

interface ApiService {
    @Headers(
        "accept: application/json, text/plain, /",
        "content-type: application/json"
    )
    @POST("GetCheap")
    suspend fun getTickets(
        @Body body: BodyRequest
    ): Response<Flights>
}

data class BodyRequest(
    val startLocationCode: String = "LED"
) {}
