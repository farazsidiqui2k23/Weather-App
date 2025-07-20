package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.collection.emptyIntSet
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ApiSetup.Api_keys
import com.example.weatherapp.ApiSetup.NetworkResponse
import com.example.weatherapp.ApiSetup.retrofitInstance
import com.example.weatherapp.ApiSetup.weatherViewModel
import com.example.weatherapp.Location.getUserLocation


import com.example.weatherapp.fakeAPItesting.AlbumData
import com.example.weatherapp.fakeAPItesting.AlbumService
import com.example.weatherapp.fakeAPItesting.Albums_Class
import com.example.weatherapp.fakeAPItesting.RetrofitInstance
import com.example.weatherapp.screens.Home_Screen
import com.example.weatherapp.screens.main_scr
import com.example.weatherapp.screens.splash
import com.example.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.create
import java.util.Date


class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {






            WeatherAppTheme {

//                Scaffold { padding ->
////                    Home_Screen(Modifier.padding(padding))
//                                    splash(Modifier.padding(padding))
//                }

            var cordinates =  getUserLocation(this)
//                println(cordinates)

            }
        }
    }
}

