package com.example.patitapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore para guardar datos de usuario simples
val Context.dataStore by preferencesDataStore("user_prefs")

class DataStoreManager(private val context: Context) {

    // CAMBIO: agrego función "saveUser" para guardar el usuario actual --> recordar qué usuario inició sesión
    // para que HomeScreen lo salude por su nombre

    suspend fun saveUser(nombre: String) {
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario_actual")] = nombre
        }
    }

    // CAMBIO: agrego función "getCurrentUser" para obtener el usuario actual
    // HomeScreen leerá  (Flow<String?>) para mostrar el saludo personalizado

    fun getCurrentUser(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[stringPreferencesKey("usuario_actual")]
        }
    }
}
