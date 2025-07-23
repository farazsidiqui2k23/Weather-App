package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.screens.HomeScreen
import com.example.weatherapp.screens.MainScreen

import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.theme.dark_purple
import com.example.weatherapp.ui.theme.purple
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.Date


const val DEBUG = "FarazSidd"

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {


                Scaffold { padding ->
                    WeatherApp(this, Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun WeatherApp(context: Context, modifier: Modifier) {

    val systemUIcontroller = rememberSystemUiController()
    val statusBarColor = purple

    SideEffect {
        systemUIcontroller.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = false
        )
    }

    val navController = rememberNavController()

    var RequiredCity by remember { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "Main_Screen") {
        composable("Main_Screen") {
            MainScreen(
                context = context,
                modifier = modifier,
                navController = navController
            ) { city ->
                RequiredCity = city
            }
        }

        composable("Home_Screen") {
            HomeScreen(
                modifier = modifier,
                context = context,
                city = RequiredCity,
                search = true,
            )
        }
    }


}
