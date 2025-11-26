package com.example.patitapp.view

import android.Manifest
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.example.patitapp.R
import com.example.patitapp.location.DirectionsClient
import com.example.patitapp.location.LocationProvider
import com.example.patitapp.model.Service
import com.example.patitapp.utils.Distance
import com.example.patitapp.viewmodel.ServiceViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(
    entry: NavBackStackEntry,
    onBack: () -> Unit,
    // Inyectamos el ViewModel
    viewModel: ServiceViewModel = viewModel()
) {
    val category = entry.arguments?.getString("category")
    val ctx = LocalContext.current
    val act = ctx as Activity

    // --- ESTADOS DE UI ---
    var showMap by remember { mutableStateOf(false) }
    var myLat by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var route by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    // --- DATOS DE LA NUBE (ViewModel) ---
    val services by viewModel.services.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val scope = rememberCoroutineScope()

    // --- LAUNCHER DE PERMISOS CORREGIDO ---
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // CORRECCIÓN: Usamos scope.launch porque getLastKnownLatLng es suspendida
            scope.launch {
                myLat = LocationProvider.getLastKnownLatLng(activity = act)
            }
        }
    }

    // --- INICIALIZACIÓN (Ubicación) ---
    LaunchedEffect(Unit) {
        if (!LocationProvider.hasCoarsePermission(act)) {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            // Aquí ya estamos en una corrutina (LaunchedEffect), así que no necesitamos scope.launch extra
            myLat = LocationProvider.getLastKnownLatLng(act)
        }

        // Fallback si falla el GPS o es emulador sin configurar
        if (myLat == null) {
            myLat = -33.024 to -71.551
        }
    }

    // --- FILTRADO DE SERVICIOS ---
    val filtered = remember(category, services) {
        if (category.isNullOrBlank()) services else services.filter { it.category == category }
    }

    // --- CÁLCULO DE RUTA AL SELECCIONAR ---
    LaunchedEffect(selectedService) {
        if (selectedService != null && myLat != null) {
            scope.launch {
                val origin = LatLng(myLat!!.first, myLat!!.second)
                val destination = LatLng(selectedService!!.lat, selectedService!!.lon)
                // Asegúrate de que tu DirectionsClient maneje errores y no crashee si falla la red
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
                            selectedService = null
                            route = emptyList()
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
        // Si no hay ubicación o está cargando, mostramos carga
        if (isLoading && services.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (myLat == null) {
            // Caso raro: No hay ubicación ni datos
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                Text("Esperando ubicación...")
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
                    Marker(state = MarkerState(LatLng(lat, lon)), title = "Tú estás aquí")

                    selectedService?.let {
                        Marker(state = MarkerState(LatLng(it.lat, it.lon)), title = it.name)
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
                                .clickable { selectedService = s },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // --- IMAGEN CON COIL (NUBE) ---
                                AsyncImage(
                                    // Intenta cargar la URL (imageUrl), si es null usa el recurso local (imageRes)
                                    model = s.imageUrl ?: s.imageRes,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    // Placeholder mientras carga
                                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                                    // Si falla la carga de la URL, usa el recurso local (o un icono por defecto)
                                    error = painterResource(if(s.imageRes != 0) s.imageRes else R.drawable.ic_launcher_foreground)
                                )

                                Spacer(Modifier.width(16.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(text = s.name, style = MaterialTheme.typography.titleMedium)
                                    Text(text = s.description, style = MaterialTheme.typography.bodyMedium)

                                    val price = if (s.priceCLP > 0) "$${s.priceCLP}" else "Gratis"
                                    Text(
                                        text = "$price • ${"%.1f".format(km)} km",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}