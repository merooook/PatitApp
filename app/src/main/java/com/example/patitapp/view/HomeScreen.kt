package com.example.patitapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.patitapp.data.DataStoreManager
import kotlinx.coroutines.launch

// Pantalla Home (Compose)
// - Saluda al usuario con el nombre guardado en DataStore (tomamos el primer usuario de la lista).
// - Muestra la pregunta "¿Qué quieres hacer hoy?"
// - Ofrece 3 acciones (botones) que navegan a la lista filtrada por categoría:
 //  - Veterinaria 24h  -> services?category=vet24
 //  - Servicios        -> services?category=servicio
 //  - Tiendas          -> services?category=tienda

@Composable
fun HomeScreen(nav: NavController) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStoreManager = remember { DataStoreManager(ctx) }

    // Estado local para mostrar el nombre del usuario actual
    var name by remember { mutableStateOf("Patit@") }


    // CAMBIO: no lee lista completa de usuarios --> se obtiene solo el usuario actual guardado con saveUser().

    LaunchedEffect(Unit) {
        scope.launch {
            dataStoreManager.getCurrentUser().collect { nombre ->
                if (!nombre.isNullOrBlank()) {
                    name = nombre
                }
            }
        }
    }

    // Diseño de la pantalla

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(60.dp))

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
        Button(
            onClick = { nav.navigate("services?category=vet24") },
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