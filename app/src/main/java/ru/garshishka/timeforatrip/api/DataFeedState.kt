package ru.garshishka.timeforatrip.api

sealed interface DataFeedState {
    object Idle : DataFeedState
    object Error : DataFeedState
    object Loading : DataFeedState
}