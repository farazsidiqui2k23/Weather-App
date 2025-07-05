package com.example.weatherapp.ApiSetup

import com.example.weatherapp.ApiSetup.Data_class.weatherDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface weather_Api_Interface {

    @GET("/v1/forecast.json")
    suspend fun getWeather(
        @Query("key") apiKey: String, @Query("q") city: String,
        @Query("days") day: Int,
        @Query("aqi") aqi: String, @Query("alerts") alerts: String
    ): Response<weatherDataClass>

}