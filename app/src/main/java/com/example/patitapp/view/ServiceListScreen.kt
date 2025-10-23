package com.example.patitapp.view

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable //  necesario para seleccionar servicio
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface //  para barra inferior con botón
import androidx.compose.material3.Button // botón “Direcciones en mapa”
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
// Imports para flecha “volver”
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton


// Pantalla de lista de servicios.
// - Lee el parámetro de navegación "category" (vet24 | servicio | tienda)
// - Pide permiso de ubicación COARSE y obtiene lastLocation (o usa fallback Viña)
// - Calcula distancia y la muestra junto al servicio.

// CAMBIO: Se permite seleccionar un servicio y luego se muestra un botón "Direcciones en mapa" para abrir la app de mapas/navegador nativo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(entry: NavBackStackEntry, onBack: () -> Unit) {
    val category = entry.arguments?.getString("category") // puede ser null o ""

    val ctx = LocalContext.current
    val act = ctx as Activity

    // Estado con la ubicación del usuario (lat, lon)
    var myLat by remember { mutableStateOf<Pair<Double, Double>?>(null) }

    // Servicio seleccionado para habilitar botón inferior

    var selectedService by remember { mutableStateOf<Service?>(null) }

    // Lanzador de permiso en tiempo de ejecución para ACCESS_COARSE_LOCATION

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* granted: Boolean -> no necesitamos manejar acá, igual haremos fallback */ }

    // Pregunta permiso para intentar obtener lastLocation una vez

    LaunchedEffect(Unit) {
        if (!LocationProvider.hasCoarsePermission(act)) {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            myLat = LocationProvider.getLastKnownLatLng(act)
        }
        // Fallback si no hay fix disponible (ej: sin ubicación)

        if (myLat == null) {
            myLat = -33.024 to -71.551 // Viña aprox
        }
    }

    // Filtrado por categoría (si viene vacia o null mostramos todos)

    val base = remember { MockData.services }
    val filtered = remember(category) {
        if (category.isNullOrBlank()) base else base.filter { it.category == category }
    }

    // Función para abrir el mapa según el servicio seleccionado

    fun openInMaps(service: Service) {
        val uri = Uri.parse("geo:${service.lat},${service.lon}?q=${service.lat},${service.lon}(${Uri.encode(service.name)})")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            ctx.startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            // Si no hay app de mapas instalada, abre en navegador
            val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${service.lat},${service.lon}")
            ctx.startActivity(Intent(Intent.ACTION_VIEW, webUri))
        }
    }

    // CAMBIO: Scaffold con bottomBar --> botón de direcciones y flecha volver

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Servicios cercanos") },
                // Flecha para volver a la pantalla anterior (Home)
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },

        bottomBar = {
            // Muestra el botón solo si hay un servicio seleccionado
            if (selectedService != null) {
                Surface(tonalElevation = 3.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedService!!.name,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(12.dp))
                        Button(onClick = { openInMaps(selectedService!!) }) {
                            Text("Direcciones en mapa")
                        }
                    }
                }
            }
        }
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
                    // CAMBIO: los items ahora son clicables para seleccionar
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .clickable { selectedService = s }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(s.imageRes),
                                contentDescription = null,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(text = s.name)
                                Text(text = s.description)
                                val price = if (s.priceCLP > 0) "${s.priceCLP} CLP" else "—"
                                Text(text = "$price • ${"%.1f".format(km)} km")
                            }
                        }
                    }
                }
            }
        }
    }
}
