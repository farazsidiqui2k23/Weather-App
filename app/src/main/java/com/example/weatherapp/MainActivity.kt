package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.collection.emptyIntSet
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData

import com.example.weatherapp.fakeAPItesting.AlbumData
import com.example.weatherapp.fakeAPItesting.AlbumService
import com.example.weatherapp.fakeAPItesting.Albums_Class
import com.example.weatherapp.fakeAPItesting.RetrofitInstance
import com.example.weatherapp.ui.theme.WeatherAppTheme
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {

                val retrofitService =
                    RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

                val responceLiveData: LiveData<Response<Albums_Class>> =
                    liveData {
                        val response = retrofitService.getAlbum()
                        emit(
                            value = response
                        )
                    }


                responceLiveData.observe(this, Observer {
                    val albumList = it.body()

                    Log.d("faraz", albumList.toString())

//                    if (albumList != null) {
//                        while (albumList.hasNext()) {
//
//                            val albumItem = albumList.next()
//
//                            val title = albumItem.title
//
//
//
//                        }
//                    } else {
//                        println("Empty")
//                    }

                })


            }
        }
    }
}

@Composable
fun Greeting(title: String) {
    Column {
        Text(
            text = title
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting("Android")
    }
}