package com.example.patitapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.patitapp.data.DataStoreManager
import kotlinx.coroutines.flow.first
import androidx.compose.foundation.layout.padding import kotlinx.coroutines.launch

/**
 * Pantalla Home (Compose)
 * - Saluda al usuario con el nombre guardado en DataStore (tomamos el primer usuario de la lista).
 * - Muestra la pregunta "¿Qué quieres hacer hoy?"
 * - Ofrece 3 acciones (botones) que navegan a la lista filtrada por categoría:
 *   - Veterinaria 24h  -> services?category=vet24
 *   - Servicios        -> services?category=servicio
 *   - Tiendas          -> services?category=tienda
 */

@Composable
fun HomeScreen(nav: NavController) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStoreManager = remember { DataStoreManager(ctx) }

    // Estado con el nombre a mostrar en el saludo.
    var name by remember { mutableStateOf("Patit@") }

    // Leemos los usuarios guardados una sola vez al entrar a la pantalla.
    LaunchedEffect(Unit) {
        // Obtenemos la lista completa (como tú ya la guardas en JSON).
        val users = dataStoreManager.getUsers().first()
        if (users.isNotEmpty()) {
            // ⚠️ IMPORTANTE: si tu data class Usuario usa otro campo para el nombre,
            // reemplaza ".nombre" por el que corresponda (p. ej., ".name", ".userName").
            runCatching { users.first() }
                .onSuccess { u ->
                    try {
                        // Línea típica si tu Usuario tiene "nombre"
                        val nombreField = u.javaClass.getDeclaredField("nombre")
                        nombreField.isAccessible = true
                        val valor = (nombreField.get(u) as? String)?.takeIf { it.isNotBlank() }
                        name = valor ?: name
                    } catch (_: NoSuchFieldException) {
                        // Si no existe "nombre", deja el placeholder "Patit@".
                        // (Cámbialo manualmente a la propiedad correcta si sabes cómo se llama)
                    }
                }
        }
    }

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
