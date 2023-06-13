package ru.garshishka.timeforatrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class TripViewModel : ViewModel() {
    private val _tripsData = MutableLiveData<String>()
    val tripsData: LiveData<String>
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
            _tripsData.value =
                "code - ${response.code()} message - ${response.message()} er - ${response.errorBody()}}"

        } else {
            val trips = response.body() ?: throw RuntimeException("body is null")
            _tripsData.value = trips.toString()
        }
    }
}