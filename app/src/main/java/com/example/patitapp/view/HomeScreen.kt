package com.example.patitapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.patitapp.data.DataStoreManager
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController) {
    val ctx = LocalContext.current
    // No necesitamos remember { DataStoreManager... }, es mejor instanciarlo directo o inyectarlo
    val dataStoreManager = remember { DataStoreManager(ctx) }

    // 1. LEER USUARIO REAL (Reactivo)
    // collectAsState convierte el flujo de datos en una variable de estado Compose
    val currentUser by dataStoreManager.getCurrentUser().collectAsState(initial = null)

    // 2. FORMATEAR NOMBRE
    val displayName = remember(currentUser) {
        if (!currentUser.isNullOrBlank()) {
            // Si tiene @ (es correo), tomamos lo de antes del arroba
            if (currentUser!!.contains("@")) {
                currentUser!!.substringBefore("@")
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            } else {
                // Si no es correo (es nombre usuario), solo mayúscula inicial
                currentUser!!.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            }
        } else {
            "Amigo" // Fallback si no hay nadie logueado
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio") },
                // El icono de volver en el Home suele ser raro si es la pantalla principal,
                // pero lo dejo si tu flujo lo requiere.
                navigationIcon = {
                    IconButton(onClick = {
                        // Verificamos si se puede volver para evitar cerrar la app
                        if (nav.previousBackStackEntry != null) nav.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            // 3. CENTRADO VERTICAL (Para que quede al medio de la pantalla)
            verticalArrangement = Arrangement.Center
        ) {

            // Título Saludo
            Text(
                text = "¡Hola, $displayName!",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "¿Qué quieres hacer hoy?",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            // --- BOTONES DE NAVEGACIÓN ---
            // Nota: Ya no pedimos permisos aquí. ServiceListScreen se encarga de eso.
            // Esto hace la navegación instantánea.

            BotonMenuHome(text = "Veterinaria 24h") {
                nav.navigate("services?category=vet24")
            }

            Spacer(Modifier.height(16.dp))

            BotonMenuHome(text = "Servicios (baño, uñas, peluquería)") {
                nav.navigate("services?category=servicio")
            }

            Spacer(Modifier.height(16.dp))

            BotonMenuHome(text = "Tiendas de mascotas") {
                nav.navigate("services?category=tienda")
            }

            // Espacio extra al final
            Spacer(Modifier.height(48.dp))

            // Opción de Salir
            TextButton(onClick = {
                nav.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }) {
                Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

// Componente reutilizable para que los botones sean idénticos
@Composable
fun BotonMenuHome(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp), // Un poco más altos para facilitar el toque
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}