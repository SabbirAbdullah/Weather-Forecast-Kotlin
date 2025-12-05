package com.weatherforecast.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await
import java.util.Locale

class LocationManager(private val context: Context) {

    private val fusedClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        // Try last location first
        val last = try {
            fusedClient.lastLocation.await()
        } catch (_: Exception) { null }

        if (last != null) return last

        // Fallback: high accuracy
        return try {
            fusedClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
        } catch (_: Exception) { null }
    }

    @SuppressLint("MissingPermission")
    suspend fun getCityName(lat: Double, lon: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            var cityName = "Unknown"

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                // Async version for Android 33+
                val listener = object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: List<android.location.Address>) {
                        cityName = addresses.firstOrNull()?.locality ?: "Unknown"
                    }
                    override fun onError(errorMessage: String?) {
                        cityName = "Unknown"
                    }
                }
                geocoder.getFromLocation(lat, lon, 1, listener)
                // Note: still need to suspend until result (or keep previous version for simplicity)
            } else {
                val results = geocoder.getFromLocation(lat, lon, 1)
                cityName = results?.firstOrNull()?.locality ?: "Unknown"
            }

            cityName
        } catch (_: Exception) {
            "Unknown"
        }
    }

}
