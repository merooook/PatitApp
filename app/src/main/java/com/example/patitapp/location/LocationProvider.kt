package com.example.patitapp.location

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

// Proveedor de ubicación con permisos y obtención de coordenadas actuales usando FusedLocationProviderClient
// Codigo unificado:
// Verifica permisos -> obtiene la última ubicación conocida O solicita una nueva si no hay registro previo

object LocationProvider {

    const val REQ_CODE = 101

    //  Funciones para verificar y solicitar permisos básicos
    fun hasCoarsePermission(activity: Activity): Boolean =
        ContextCompat.checkSelfPermission(
            activity, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    fun hasFinePermission(activity: Activity): Boolean =
        ContextCompat.checkSelfPermission(
            activity, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    fun requestLocationPermissions(activity: Activity) {
        // CAMBIO: Unificacion de coarse + fine para evitar duplicar solicitudes
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQ_CODE
        )
    }

    // CAMBIO: nuevo metodo --> getCurrentLatLng() para obtener coordenadas actuales con mayor precision
    // getLastKnownLatLng() podía devolver null si no había ubicación reciente

    suspend fun getCurrentLatLng(activity: Activity): Pair<Double, Double>? =
        suspendCancellableCoroutine { cont ->
            val client = LocationServices.getFusedLocationProviderClient(activity)

            // Obtener ubicación actual con prioridad
            try {
                client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
                    .addOnSuccessListener { loc ->
                        if (loc != null) cont.resume(loc.latitude to loc.longitude)
                        else {
                            // Si falla toma la última ubicación conocida (fallback)
                            client.lastLocation
                                .addOnSuccessListener { last ->
                                    if (last != null) cont.resume(last.latitude to last.longitude)
                                    else cont.resume(null)
                                }
                                .addOnFailureListener { cont.resume(null) }
                        }
                    }
                    .addOnFailureListener { cont.resume(null) }
            } catch (e: SecurityException) {
                // Si no tiene permiso, devolvemos null en lugar de crashear
                cont.resume(null)
            }
        }

    // Se mantiene por compatibilidad con pantallas antiguas que la usen (se deja como alternativa / fallback)
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
