package com.example.weatherapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.dark_purple
import com.example.weatherapp.ui.theme.ghostWhite
import com.example.weatherapp.ui.theme.purple

@Composable
fun splash(modifier: Modifier) {
    val brush = Brush.linearGradient(listOf(dark_purple, ghostWhite, purple))
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
            }
            Button(
                onClick = {},
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

@Preview
@Composable
private fun ui() {
    splash(modifier = Modifier)
}