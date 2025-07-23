package com.example.weatherapp.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.DEBUG
import com.example.weatherapp.Location.CurrentLocationResponse
import com.example.weatherapp.Location.getUserLocation
import com.example.weatherapp.Location.get_current_city
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.dark_purple
import com.example.weatherapp.ui.theme.ghostWhite
import com.example.weatherapp.ui.theme.purple

@Composable
fun MainScreen(
    context: Context,
    modifier: Modifier,
    navController: NavController,
    onClick: (city: String) -> Unit
) {
    val brush = Brush.linearGradient(listOf(dark_purple, ghostWhite, purple))

    var allowNavigation by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "",
//                modifier = Modifier.scale(scaleY = 1f, scaleX = -1f)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Weather",
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    fontSize = 42.sp, color = dark_purple
                )
                Text(
                    text = "Forecasts",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 48.sp, color = purple
                )
                Spacer(Modifier.height(30.dp))
                CurrentCity(context) { bool, city ->
                    allowNavigation = bool
                    onClick(city)
                }
            }
            Button(
                onClick = {
                    //from here we go to the home screen
                    navController.navigate("Home_Screen")
                },
                enabled = allowNavigation,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
                    .width(200.dp)
                    .background(
                        Brush.horizontalGradient(listOf(purple, dark_purple)),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Text("Get Start", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun CurrentCity(context: Context, isNavAllow: (bool: Boolean, city: String) -> Unit) {

    var current_city by rememberSaveable { mutableStateOf("") }

    val LocationResult = getUserLocation(context)

    when (LocationResult) {
        is CurrentLocationResponse.loading -> {

//            Toast.makeText(
//                context,
//                "Please turn on Location & stable your internet connection",
//                Toast.LENGTH_SHORT
//            ).show()

            CircularProgressIndicator()
        }

        is CurrentLocationResponse.OnSucess -> {

            current_city = get_current_city(context, data = LocationResult.cordinates)

            isNavAllow(true, current_city)


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Place,
                    contentDescription = "Location Icon",
                    tint = dark_purple, modifier = Modifier.offset(x = 0.dp, y = -2.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = current_city,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    fontSize = 22.sp
                )
            }
            Log.d(DEBUG, "Location Result ${LocationResult.cordinates.toString()}")

        }

        is CurrentLocationResponse.OnErorr -> {

            Toast.makeText(context, LocationResult.message, Toast.LENGTH_SHORT).show()
        }

    }

}


//@Preview
//@Composable
//private fun ui() {
//    splash(context = LocalContext.current, modifier = Modifier)
//}