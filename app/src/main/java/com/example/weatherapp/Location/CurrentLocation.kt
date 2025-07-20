package com.example.weatherapp.Location

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.LauncherActivity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import kotlin.math.absoluteValue


private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@SuppressLint("MissingPermission")
@Composable
fun getUserLocation(context: Context): Lat_Long {

    var UserLocation by remember { mutableStateOf(Lat_Long()) }

    var grantPermission by remember { mutableStateOf(false) }

    // The Fused Location Provider provides access to location APIs.
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val requestLocationPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permission ->
            val coarseLocationGranted =
                permission[ACCESS_COARSE_LOCATION] == true
            println("ACCESS_COARSE_LOCATION ${coarseLocationGranted}")
            val fineLocationGranted = permission[ACCESS_FINE_LOCATION] == true
            println("ACCESS_FINE_LOCATION ${fineLocationGranted}")
            if (coarseLocationGranted && fineLocationGranted) {
                grantPermission = true
            }
        }
    )

    //for location enable or not
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    LaunchedEffect(Unit) {

        if (!context.hasLocationPermission()) {
            Log.d("FarazSidd", "Don't have permission")
            requestLocationPermission.launch(
                arrayOf(
                    ACCESS_COARSE_LOCATION,
                    ACCESS_FINE_LOCATION
                )
            )
        } else {
            grantPermission = true
        }
    }

    if (grantPermission) {
        Log.d("FarazSidd", "have permission")
        DisposableEffect(Unit) {
            Log.d("FarazSidd", "Going to Fetch location")
            UserLocation = locationUpdate()
            onDispose { }
        }
    }
    return UserLocation
}


@SuppressLint("MissingPermission")

fun locationUpdate (onResult : (LocationResult) -> Unit){

    fusedLocationProviderClient.lastLocation
        .addOnSuccessListener() { location ->
            location.let {
                if (location != null) {
                    val lat = location.latitude
                    val long = location.longitude
                    //call sealed class
                    onResult(LocationResult.OnSucess(Lat_Long(location.latitude, location.longitude)))

                    Log.d("FarazSidd", "Last location ${lat} ${long}")
                } else {
                    //here we get current location
                    fusedLocationProviderClient.getCurrentLocation(
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                        CancellationTokenSource().token
                    ).addOnSuccessListener() { location ->
                        location.let {
                            val lat = location.latitude
                            val long = location.longitude
                            //call sealed class
                            onResult(LocationResult.OnSucess(Lat_Long(location.latitude, location.longitude)))
                            Log.d("FarazSidd", "Current location : ${lat} && ${long}")
                        }
                    }.addOnFailureListener() {
                        Log.d("FarazSidd", "Failed to get CURRENT LOCATION")
                    }
                }
            }
        }
        .addOnFailureListener() {
            Log.d("FarazSidd", "Failed to get LAST LOCATION")
        }
}

data class Lat_Long(
    val Latitude: Double,
    val Longitude: Double
)

sealed class LocationResult{
    object loading : LocationResult()
    data class OnSucess(val cordinates : Lat_Long) : LocationResult()
    data class OnErorr(val message : String) : LocationResult()
}