package ru.garshishka.timeforatrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.BuildConfig
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.garshishka.timeforatrip.dto.Flights

class TripViewModel : ViewModel() {
    private val _tripsData = MutableLiveData<Flights>()
    val tripsData: LiveData<Flights>
        get() = _tripsData

    fun load() = viewModelScope.launch {
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

        val apiService = retrofit.create<ApiService>()


        val response = apiService.getTickets(BodyRequest())
        if (!response.isSuccessful) {
            throw RuntimeException(response.message().toString())
        }
        val trips = response.body() ?: throw RuntimeException("body is null")
        _tripsData.value = trips
    }
}
