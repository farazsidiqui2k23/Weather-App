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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier, context: Context, city: String, search: Boolean) {
    val brush = Brush.linearGradient(listOf(dark_purple, ghostWhite, purple))
    var city by remember { mutableStateOf(city) }
    var search by remember { mutableStateOf(search) }

    var forecast: Forecast? by remember { mutableStateOf(null) }

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
                            focusedContainerColor = ghostWhite
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
                                CircularProgressIndicator()
                            }

                        }

                        is NetworkResponse.Success -> {
                            main_scr(
                                currentLocation = result.data.location,
                                currentWeather = result.data.current
                            )

                            forecast = result.data.forecast


                        }

                        null -> {
                            println("Still null")
                        }

                        //here end
                    }
                }
            }

            forecast?.let {
                forecast_display(modifier, it)
            }


        }
    }


}


@Composable
fun forecast_display(modifier: Modifier, forecast: Forecast) {

    val weekDays = mutableListOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var i by rememberSaveable { mutableIntStateOf(0) }
    LazyRow(modifier = Modifier.padding(12.dp)) {
        items(weekDays) { day ->

            if (i > 2) {
                i %= 3
            }

            var dateEpoch = forecast.forecastday[i].date_epoch

            var date = Date(dateEpoch.toLong() * 1000)

            var forecast_day = date.toString().substring(0..2)

            if (forecast_day == day) {

                day_forecast(
                    modifier = modifier,
                    index = i,
                    day = forecast_day,
                    celcius = req_str(forecast.forecastday[i].hour[0].temp_c),
                    farhen = req_str(forecast.forecastday[i].hour[0].temp_f)
                )
                Spacer(Modifier.width(6.dp))
                i += 1
            } else {

                day_forecast(
                    modifier = modifier,
                    index = 8,
                    day = day,
                    celcius = "32",
                    farhen = "21"
                )
                Spacer(Modifier.width(6.dp))
            }


        }

    }
}


@Composable
fun main_scr(currentLocation: Location, currentWeather: Current) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.padding(0.dp, 24.dp, 0.dp, 0.dp)) {
            Icon(Icons.Default.LocationOn, contentDescription = "current loc", tint = dark_purple)
            Spacer(Modifier.width(16.dp))

            Text(text = currentLocation.name)
        }

        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxHeight(.3f)
        ) {
            Text(
                req_str(currentWeather.temp_c),
                fontSize = 130.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = Color(0xFF4B4EE5)
            )

            Text(
                "\u00B0",
                fontSize = 100.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = Color(0xFF4B4EE5)
            )

        }
        Row() {
            Text(
                currentWeather.condition.text,
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
//                            modifier = Modifier.offset(x = 0.dp, y = -50.dp) ,
                color = Color(0xFF4B4EE5)
            )
            Image(
                painter = painterResource(id= weatherIcon(currentWeather.condition.code.toInt())),
                contentDescription = "Weather Image",
                modifier = Modifier
                    .offset(x = 25.dp, y = -20.dp)
                    .size(100.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
//                            .offset(y = -50.dp)
//                            .weight(1f)
            , contentAlignment = Alignment.BottomCenter

        ) {
            weatherProperties(currentWeather)
        }
    }

}

fun weatherIcon(code : Int) : Int{
        var image = when(code) {
            1000 -> R.drawable.sunny
            1003 -> R.drawable.partly_cloudy
            1006, 1009 -> R.drawable.cloudy
            1063, 1180-1186 -> R.drawable.rainy



            else -> { R.drawable.partly_cloudy}
        }

    return image

}
@Composable
fun weatherProperties(currentWeather: Current) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            ghostWhite
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Column(Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                MoreProperty(
                    icon = R.drawable.feels_like,
                    value = req_str(currentWeather.feelslike_c) + "\u00B0",
                    label = "Feels like"
                )

                MoreProperty(
                    icon = R.drawable.humidity,
                    value = req_str(currentWeather.humidity),
                    label = "Humidity"
                )
                MoreProperty(
                    icon = R.drawable.wind,
                    value = req_str(currentWeather.wind_kph) + "km/h",
                    label = "Wind speed"
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                MoreProperty(
                    icon = R.drawable.visibility,
                    value = req_str(currentWeather.vis_km),
                    label = "Visibility"
                )

                MoreProperty(
                    icon = R.drawable.pressure,
                    value = req_str(currentWeather.precip_in),
                    label = "Pressure"
                )
                MoreProperty(
                    icon = R.drawable.maxtemp,
                    value = "32",
                    label = "Max temp"
                )

            }
        }
    }
}


@Composable
fun MoreProperty(icon: Int, value: String, label: String) {

    Box {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Feels like",
                colorFilter = ColorFilter.tint(dark_purple),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = value,
                color = dark_purple,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
            )

            Text(
                text = label,
                color = dark_purple,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )

        }
    }


}

@Composable
fun day_forecast(modifier: Modifier = Modifier, index : Int,day: String, celcius: String, farhen: String) {


    Card(
        modifier = Modifier.width(70.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(if(index == 0) dark_purple else ghostWhite.copy(alpha = .6f)),

        ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = day,
                    color = if (index == 0) Color.White else dark_purple,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                )
                Spacer(Modifier.height(4.dp))

                Image(
                    painter = painterResource(id = R.drawable.sunny),
                    contentDescription = "Feels like",
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = "${celcius}\u00B0",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${farhen}F",
                    color = if (index == 0) ghostWhite else dark_purple,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )

            }
        }
    }
}

fun req_str(celcius: String): String {
    return celcius.split(".")[0]
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun GreetingPreview() {
//    WeatherAppTheme {
//        Home_Screen(modifier = Modifier)
//
////        weatherProperties()
//    }
//}

@Preview
@Composable
private fun ui() {
//    MoreProperty()
//    weatherProperties()
//    day_forecast()
}