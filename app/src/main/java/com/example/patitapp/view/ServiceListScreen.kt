package com.example.patitapp.view

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.patitapp.data.MockData
import com.example.patitapp.location.LocationProvider
import com.example.patitapp.model.Service
import com.example.patitapp.utils.Distance

// Pantalla de lista de servicios.
// - Lee el parámetro de navegación "category" (vet24 | servicio | tienda)
// - Pide permiso de ubicación COARSE y obtiene lastLocation (o usa fallback Viña)
// - Calcula distancia y la muestra junto al servicio.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(entry: NavBackStackEntry, onBack: () -> Unit) {
    val category = entry.arguments?.getString("category") // puede ser null o ""

    val ctx = LocalContext.current
    val act = ctx as Activity

    // Estado con la ubicación del usuario (lat, lon)
    var myLat by remember { mutableStateOf<Pair<Double, Double>?>(null) }

    // Lanzador de permiso en tiempo de ejecución para ACCESS_COARSE_LOCATION
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* granted: Boolean -> no necesitamos manejar acá, igual haremos fallback */ }

    // Pedimos permiso y tratamos de obtener lastLocation una vez.
    LaunchedEffect(Unit) {
        if (!LocationProvider.hasCoarsePermission(act)) {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            myLat = LocationProvider.getLastKnownLatLng(act)
        }
        // Fallback si no hay fix disponible (ej: emulador sin ubicación)
        if (myLat == null) {
            myLat = -33.024 to -71.551 // Viña aprox
        }
    }

    // Filtrado por categoría (si viene vacía o null, mostramos todo).

    val base = remember { MockData.services }
    val filtered = remember(category) {
        if (category.isNullOrBlank()) base else base.filter { it.category == category }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Servicios cercanos") }) }
    ) { pad ->
        if (myLat == null) {
            // Aún sin ubicación/fallback: loader
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(pad),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        } else {
            val (lat, lon) = myLat!!
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
            ) {
                items(filtered) { s ->
                    val km = Distance.kmBetween(lat, lon, s.lat, s.lon)
                    ServiceRow(service = s, km = km)
                }
            }
        }
    }
}

@Composable
private fun ServiceRow(service: Service, km: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(service.imageRes),
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(text = service.name)
                Text(text = service.description)
                val price = if (service.priceCLP > 0) "${service.priceCLP} CLP" else "—"
                Text(text = "$price • ${"%.1f".format(km)} km")
            }
        }
    }
}
