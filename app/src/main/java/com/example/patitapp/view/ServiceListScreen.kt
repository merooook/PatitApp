package com.example.patitapp.view

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.patitapp.data.MockData
import com.example.patitapp.location.DirectionsClient
import com.example.patitapp.location.LocationProvider
import com.example.patitapp.model.Service
import com.example.patitapp.utils.Distance
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import androidx.activity.compose.BackHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(entry: NavBackStackEntry, onBack: () -> Unit) {
    val category = entry.arguments?.getString("category")
    val ctx = LocalContext.current
    val act = ctx as Activity

    var showMap by remember { mutableStateOf(false) }
    var myLat by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var route by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    val scope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        if (!LocationProvider.hasCoarsePermission(act)) {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            myLat = LocationProvider.getLastKnownLatLng(act)
        }
        if (myLat == null) {
            myLat = -33.024 to -71.551 // Fallback Viña
        }
    }

    val base = remember { MockData.services }
    val filtered = remember(category) {
        if (category.isNullOrBlank()) base else base.filter { it.category == category }
    }

    // Al seleccionar un servicio, mostramos el mapa y buscamos la ruta
    LaunchedEffect(selectedService) {
        if (selectedService != null && myLat != null) {
            scope.launch {
                val origin = LatLng(myLat!!.first, myLat!!.second)
                val destination = LatLng(selectedService!!.lat, selectedService!!.lon)
                route = DirectionsClient.getDirections(origin, destination)
                showMap = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Servicios cercanos") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (showMap) {
                            showMap = false
                            selectedService = null // Limpiamos selección
                            route = emptyList() // Limpiamos ruta
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showMap = !showMap }) {
                        Icon(
                            imageVector = if (showMap) Icons.Default.List else Icons.Default.Map,
                            contentDescription = if (showMap) "Ver Lista" else "Ver Mapa"
                        )
                    }
                }
            )
        }
    ) { pad ->
        if (myLat == null) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val (lat, lon) = myLat!!

            if (showMap) {
                BackHandler {
                    showMap = false
                    selectedService = null
                    route = emptyList()
                }

                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(LatLng(lat, lon), 12f)
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize().padding(pad),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = LatLng(lat, lon)),
                        title = "Tú estás aquí"
                    )

                    selectedService?.let {
                        Marker(
                            state = MarkerState(position = LatLng(it.lat, it.lon)),
                            title = it.name
                        )
                    }

                    if (route.isNotEmpty()) {
                        Polyline(points = route, color = Color.Blue, width = 10f)
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(pad)) {
                    items(filtered) { s ->
                        val km = Distance.kmBetween(lat, lon, s.lat, s.lon)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .clickable { selectedService = s } // Al hacer clic, se dispara el LaunchedEffect
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
}
