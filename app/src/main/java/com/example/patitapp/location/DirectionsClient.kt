package com.example.patitapp.location

import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import com.google.maps.model.LatLng as MapsLatLng
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DirectionsClient {

    // ADVERTENCIA: No es recomendable exponer la clave de API en el código fuente.
    // Reemplaza "TU_CLAVE_DE_API_AQUÍ" con tu clave real.
    private const val apiKey = "AIzaSyDCRoapR1-SVJtze2TIZlm6_4abonkj3uM"

    private val geoApiContext: GeoApiContext by lazy {
        GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()
    }

    suspend fun getDirections(origin: LatLng, destination: LatLng): List<LatLng> =
        withContext(Dispatchers.IO) {
            try {
                val result: DirectionsResult = DirectionsApi.newRequest(geoApiContext)
                    .origin(MapsLatLng(origin.latitude, origin.longitude))
                    .destination(MapsLatLng(destination.latitude, destination.longitude))
                    .await()

                if (result.routes.isNotEmpty()) {
                    val path = result.routes[0].overviewPolyline.decodePath()
                    return@withContext path.map { LatLng(it.lat, it.lng) }
                }
            } catch (e: Exception) {
                e.printStackTrace() // Manejar el error apropiadamente
            }
            return@withContext emptyList()
        }
}
