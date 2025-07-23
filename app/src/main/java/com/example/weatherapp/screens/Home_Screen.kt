package com.example.weatherapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ApiSetup.Data_class.Current
import com.example.weatherapp.ApiSetup.Data_class.Day
import com.example.weatherapp.ApiSetup.Data_class.Forecast
import com.example.weatherapp.ApiSetup.Data_class.Location
import com.example.weatherapp.ApiSetup.Data_class.weatherDataClass
import com.example.weatherapp.ApiSetup.NetworkResponse
import com.example.weatherapp.ApiSetup.weatherViewModel
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.theme.dark_purple
import com.example.weatherapp.ui.theme.ghostWhite
import com.example.weatherapp.ui.theme.purple
import org.jetbrains.annotations.Async
import java.util.Calendar
import java.util.Date

lateinit var current_hour: String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier, context: Context, city: String, search: Boolean) {
    val brush = Brush.linearGradient(listOf(dark_purple, ghostWhite, purple))
    var city by remember { mutableStateOf(city) }
    var search by remember { mutableStateOf(search) }

    var forecast: Forecast? by remember { mutableStateOf(null) }
    current_hour = "0"

//use another composable
    val viewModel = viewModel<weatherViewModel>()
    if (search) {
        LaunchedEffect(Unit) {
            viewModel.getWeatherData(city)
            search = false
        }
    }
    Box(
        modifier
            .fillMaxSize()
            .background(brush = brush)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                shape = RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp),
                colors = CardDefaults.cardColors(ghostWhite.copy(alpha = .3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp, 10.dp, 16.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(26.dp),
                        placeholder = { Text("Search city") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = ghostWhite,
                            focusedTextColor = dark_purple,
                            unfocusedTextColor = purple
                        ),
                        singleLine = true,
                        trailingIcon = @Composable {
                            IconButton(onClick = {
                                search = true
                            }) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "search icon for city", tint = dark_purple
                                )
                            }
                        }
                    )

                    val weatherResult = viewModel.weatherResult.observeAsState()
                    when (val result = weatherResult.value) {

                        is NetworkResponse.Error -> {

                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        }

                        NetworkResponse.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                androidx.compose.material3.CircularProgressIndicator(
                                    color = dark_purple
                                )
                            }
                        }

                        is NetworkResponse.Success -> {
                            main_scr(
                                currentLocation = result.data.location,
                                currentWeather = result.data.current
                            )
                            forecast = result.data.forecast
                            val localtime = result.data.location.localtime.split(" ")
                            current_hour = localtime[1].split(":")[0]

                        }

                        null -> {
                            println("Still null")
                        }
                    }
                }
            }

            forecast?.let {
                forecast_display(modifier, it, current_hour.toInt())
            }

        }
    }
}








