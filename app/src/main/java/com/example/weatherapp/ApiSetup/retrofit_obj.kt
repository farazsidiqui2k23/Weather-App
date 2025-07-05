package com.example.weatherapp.ApiSetup

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitInstance {

    val base_URL = " https://api.weatherapi.com"

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weather_Api : weather_Api_Interface = getRetrofitInstance().create(weather_Api_Interface::class.java)

}