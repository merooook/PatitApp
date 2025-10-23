package com.example.patitapp.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.patitapp.data.DataStoreManager
import com.example.patitapp.location.LocationProvider
import kotlinx.coroutines.launch

// CAMBIO: imports para el ícono de “volver”
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

// Pantalla Home (Compose)
// - Saluda al usuario con el nombre guardado en DataStore (tomamos el primer usuario de la lista).
// - Muestra la pregunta "¿Qué quieres hacer hoy?"
// - Ofrece 3 acciones (botones) que navegan a la lista filtrada por categoría:
//   - Veterinaria 24h  -> services?category=vet24
//   - Servicios        -> services?category=servicio
//   - Tiendas          -> services?category=tienda

// CAMBIOS:  Pide autorización de ubicación justo cuando se selecciona servicio
// se agrega TopAppBar con flecha de “volver” (navegar a la pantalla anterior)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStoreManager = remember { DataStoreManager(ctx) }

    // Estado local para mostrar el nombre del usuario actual
    var name by remember { mutableStateOf("Patit@") }

    // No lee lista completa de usuarios --> se obtiene solo el usuario actual guardado con saveUser().
    LaunchedEffect(Unit) {
        scope.launch {
            dataStoreManager.getCurrentUser().collect { nombre ->
                if (!nombre.isNullOrBlank()) {
                    name = nombre
                }
            }
        }
    }

    // Pedir permisos de ubicación solo cuando se requiera

    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            pendingAction?.invoke()
        } else {
            // Si el usuario rechaza el permiso se puede navegar sin ubicación o mostrar aviso
            pendingAction?.invoke()
        }
        pendingAction = null
    }

    fun ensureLocationPermission(then: () -> Unit) {
        val fineGranted = ContextCompat.checkSelfPermission(
            ctx, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(
            ctx, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {
            then()
        } else {
            pendingAction = then
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    // CAMBIO: Se introduce Scaffold con TopAppBar la flecha de “volver”

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio") },
                navigationIcon = {
                    // Se añade flecha que navega a la pantalla anterior
                    IconButton(onClick = { nav.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        // Diseño de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(36.dp))

            Text(
                text = "¡Hola, $name!",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "¿Qué quieres hacer hoy?",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // Botón 1: Veterinaria 24h
            // CAMBIO: pide autorización de ubicación justo antes de navegar
            Button(
                onClick = {
                    ensureLocationPermission {
                        scope.launch {
                            val loc = LocationProvider.getCurrentLatLng(
                                activity = (ctx as? android.app.Activity) ?: return@launch
                            )
                            val lat = loc?.first
                            val lng = loc?.second
                            if (lat != null && lng != null) {
                                nav.navigate("services?category=vet24&lat=$lat&lng=$lng")
                            } else {
                                nav.navigate("services?category=vet24")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Veterinaria 24h")
            }

            Spacer(Modifier.height(12.dp))

            // Botón 2: Servicios (baño, corte de uñas, peluquería)
            Button(
                onClick = { nav.navigate("services?category=servicio") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Servicios (baño, uñas, peluquería)")
            }

            Spacer(Modifier.height(12.dp))

            // Botón 3: Tiendas de mascotas
            Button(
                onClick = { nav.navigate("services?category=tienda") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tiendas de mascotas")
            }
        }
    }
}
