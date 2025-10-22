package com.example.patitapp.location

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

// Proveedor minimalista para pedir permiso y obtener la última ubicación conocida (lastLocation) con la API de Fused Location
// Si no hay fix, devolvemos null y usamos fallback

object LocationProvider {
    const val REQ_CODE = 101

    fun hasCoarsePermission(activity: Activity): Boolean =
        ContextCompat.checkSelfPermission(
            activity, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    fun requestCoarsePermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQ_CODE
        )
    }

    suspend fun getLastKnownLatLng(activity: Activity): Pair<Double, Double>? =
        suspendCancellableCoroutine { cont ->
            val client = LocationServices.getFusedLocationProviderClient(activity)
            client.lastLocation
                .addOnSuccessListener { loc ->
                    if (loc != null) cont.resume(loc.latitude to loc.longitude)
                    else cont.resume(null)
                }
                .addOnFailureListener { cont.resume(null) }
        }
}
