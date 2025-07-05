package com.example.weatherapp.ApiSetup

//T refer to weatherData
sealed class NetworkResponse<out T> {

    data class Success<T>(val data : T) : NetworkResponse<T>()
    data class Error(val message : String) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()

}