package com.example.patitapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.collect

// CAMBIO: AppState ya no gestiona listas

data class Usuario(val correo: String, val password: String, val direccion: String)

class AppState(private val dataStore: DataStoreManager) {
    // CAMBIO: elimino listas de usuarios por solo el usuario activo
    var usuarioActualNombre: String? by mutableStateOf(null)
        private set

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        // CAMBIO: Usuario actual guardado en DataStore
        scope.launch {
            dataStore.getCurrentUser().collect { nombre ->
                usuarioActualNombre = nombre
            }
        }
    }

    // CAMBIO: cargarDatos() no leera listas inexistentes
    // No-op porque el init ya deja usuarioActualNombre sincronizado

    fun cargarDatos() {
    }

    suspend fun registrarUsuarioComoActivo(nombre: String) {
        dataStore.saveUser(nombre)
    }

    // CAMBIO: login activa al usuario guardando su nombre

    suspend fun login(nombre: String): Boolean {
        dataStore.saveUser(nombre)
        return true
    }

    suspend fun logout() {
        // Limpiar usuario activo
        dataStore.saveUser("")
    }
}
