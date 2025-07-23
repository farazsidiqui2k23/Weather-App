package com.example.weatherapp.Location

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.LauncherActivity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
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
import androidx.core.content.ContextCompat.startActivity
import com.example.weatherapp.DEBUG
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@SuppressLint("MissingPermission")
@Composable
fun getUserLocation(context: Context): CurrentLocationResponse {

    var locationState by remember { mutableStateOf<CurrentLocationResponse>(CurrentLocationResponse.loading) }

    var grantPermission by remember { mutableStateOf(false) }
    var GPSenable by remember { mutableStateOf(context.isDeviceGPSenable()) }

    // The Fused Location Provider provides access to location APIs.
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val requestLocationPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permission ->
            val coarseLocationGranted =
                permission[ACCESS_COARSE_LOCATION] == true
//            println("ACCESS_COARSE_LOCATION ${coarseLocationGranted}")
            val fineLocationGranted = permission[ACCESS_FINE_LOCATION] == true
//            println("ACCESS_FINE_LOCATION ${fineLocationGranted}")
            if (coarseLocationGranted && fineLocationGranted) {
                grantPermission = true
            }
        }
    )


    LaunchedEffect(Unit) {

        if (!context.hasLocationPermission()) {
            Log.d("FarazSidd", "Don't have permission")
                requestLocationPermission.launch(
                    arrayOf(
                        ACCESS_COARSE_LOCATION,
                        ACCESS_FINE_LOCATION
                    )
                )
            }


        if (!context.isDeviceGPSenable()) {
                Log.d(DEBUG, " enable location ${context.isDeviceGPSenable()}" )
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }


        if(context.hasLocationPermission() && GPSenable) {
            grantPermission = true
        }
    }

    if (grantPermission) {
        Log.d("FarazSidd", "have permission")

        DisposableEffect(GPSenable) {

            Log.d("FarazSidd", "Going to Fetch location")


            locationUpdate { result ->
                locationState = result
            }

            onDispose { }
        }
    }
    return locationState
}


@SuppressLint("MissingPermission")
fun locationUpdate(onResult: (CurrentLocationResponse) -> Unit) {

    try {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener() { location ->
                location.let {
                    if (location != null) {

                        //call sealed class
                        onResult(
                            CurrentLocationResponse.OnSucess(
                                Lat_Long(
                                    location.latitude,
                                    location.longitude
                                )
                            )
                        )

//                        Log.d("FarazSidd", "Last location ${lat} ${long}")
                    } else {
                        //here we get current location
                        fusedLocationProviderClient.getCurrentLocation(
                            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                            CancellationTokenSource().token
                        ).addOnSuccessListener() { location ->
                            location.let {

                                //call sealed class
                                onResult(
                                    CurrentLocationResponse.OnSucess(
                                        Lat_Long(
                                            location.latitude,
                                            location.longitude
                                        )
                                    )
                                )
//                                Log.d("FarazSidd", "Current location : ${lat} && ${long}")
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
    } catch (e: Exception) {
        onResult(CurrentLocationResponse.OnErorr("Failed due to ${e.message}"))
    }
}


