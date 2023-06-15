package ru.garshishka.timeforatrip

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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

fun getApiService(): ApiService{
    val logging = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }

    val okClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okClient)
        .baseUrl("https://vmeste.wildberries.ru/api/avia-service/twirp/aviaapijsonrpcv1.WebAviaService/")
        .build()

    return retrofit.create<ApiService>()
}

data class BodyRequest(
    val startLocationCode: String = "LED"
) {}
