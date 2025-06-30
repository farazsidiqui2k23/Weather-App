package com.example.weatherapp.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.theme.dark_purple
import com.example.weatherapp.ui.theme.ghostWhite
import com.example.weatherapp.ui.theme.purple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home_Screen(modifier: Modifier) {
    val brush = Brush.linearGradient(listOf(purple, ghostWhite, purple))
    var city by remember { mutableStateOf("") }

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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(26.dp),
                        placeholder = { Text("Search city") },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        trailingIcon = @Composable {
                            IconButton(onClick = {}) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "search icon for city", tint = dark_purple
                                )
                            }
                        }
                    )

                    Row(modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)) {
                        Text(
                            "22",
                            fontSize = 150.sp,
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
                            "Cloudy",
                            fontSize = 26.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            modifier = Modifier.offset(x = 0.dp, y = -50.dp),
                            color = Color(0xFF4B4EE5)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.sunny),
                            contentDescription = "Weather Image",
                            modifier = Modifier
                                .offset(x = 50.dp, y = -100.dp)
                                .size(120.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = 0.dp, y = -60.dp)
                    ) {
                        weatherProperties()
                    }
                    Text(text = "faraz")


                }
            }


        }


    }


}


@Composable
fun weatherProperties() {

    Card(
        modifier = Modifier.fillMaxWidth().height(500.dp),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            ghostWhite
        )
    ) {

        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                ,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                MoreProperty(
                    icon = R.drawable.feels_like,
                    value = "32",
                    label = "Feels like"
                )

                MoreProperty(
                    icon = R.drawable.humidity,
                    value = "32",
                    label = "Humidity"
                )
                MoreProperty(
                    icon = R.drawable.wind,
                    value = "32",
                    label = "Wind speed"
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                ,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                MoreProperty(
                    icon = R.drawable.visibility,
                    value = "32",
                    label = "Visibility"
                )

                MoreProperty(
                    icon = R.drawable.pressure,
                    value = "32",
                    label = "Pressure"
                )
                MoreProperty(
                    icon = R.drawable.wind,
                    value = "32",
                    label = "Max temp"
                )

            }
        }
    }
}


@Composable
fun MoreProperty(icon: Int, value: String, label: String) {

    Box{

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Home_Screen(modifier = Modifier)

//        weatherProperties()
    }
}

@Preview
@Composable
private fun ui() {
//    MoreProperty()
    weatherProperties()
}