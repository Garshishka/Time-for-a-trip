package ru.garshishka.timeforatrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.garshishka.timeforatrip.api.DataFeedState
import ru.garshishka.timeforatrip.dto.Flight
import ru.garshishka.timeforatrip.dto.Flights

class TripViewModel : ViewModel() {
    private val _tripsData = MutableLiveData<Flights>()
    val tripsData: LiveData<Flights>
        get() = _tripsData
    private val _dataState = MutableLiveData<DataFeedState>(DataFeedState.Idle)
    val dataState: LiveData<DataFeedState>
        get() = _dataState

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        _dataState.value = DataFeedState.Loading

        val apiService = getApiService()

        val response = apiService.getTickets(BodyRequest())
        if (!response.isSuccessful) {
            _dataState.value = DataFeedState.Error
            throw RuntimeException(response.message().toString())
        }
        val trips = response.body() ?: throw RuntimeException("body is null")
        _tripsData.value = trips
        _dataState.value = DataFeedState.Idle
    }

    fun likeFlight(flight: Flight){
        _tripsData.value?.let {data ->
            val newData = data.copy(flights = data.flights.map { if(it==flight) it.copy(likedByMe = !flight.likedByMe) else it })
            _tripsData.value = newData
        }
    }
}
