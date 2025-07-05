package com.example.weatherapp.ApiSetup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.ApiSetup.Data_class.weatherDataClass
import kotlinx.coroutines.launch

class weatherViewModel : ViewModel() {

    val weatherApi = retrofitInstance.weather_Api

    val _weatherResult = MutableLiveData<NetworkResponse<weatherDataClass>>()
    val weatherResult: LiveData<NetworkResponse<weatherDataClass>> = _weatherResult

    fun getWeatherData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {

            val response = weatherApi.getWeather(
                apiKey = Api_keys.apiKey,
                city = city,
                day = 7,
                aqi = "no",
                alerts = "no"
            )

            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error(
                        message = "Failed to Load Data"
                    )
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error(
                    message = "Failed to Load Data"
                )
            }
        }


    }
}