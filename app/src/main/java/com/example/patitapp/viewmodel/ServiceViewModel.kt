package com.example.patitapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patitapp.model.Service
import com.example.patitapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServiceViewModel : ViewModel() {

    // Estado de la lista de servicios (empieza vacía)
    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> = _services.asStateFlow()

    // Estado de carga (para mostrar la ruedita de progreso)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // Cargar datos automáticamente apenas se crea el ViewModel
        fetchServices()
    }

    fun fetchServices() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Llamada a la Nube (AWS)
                // Asegúrate de que en RetrofitClient tu variable se llame 'apiService'
                val result = RetrofitClient.apiService.getAllLocations()

                // 2. Si es exitoso, actualizamos la lista
                _services.value = result

            } catch (e: Exception) {
                // 3. Si falla (sin internet, servidor caído, etc.)
                e.printStackTrace() // Imprime el error en el Logcat para que sepas qué pasó

                // Ya NO usamos MockData. Si falla, dejamos la lista vacía o la anterior.
                _services.value = emptyList()
            } finally {
                // 4. Siempre ocultamos la ruedita de carga al terminar (haya éxito o error)
                _isLoading.value = false
            }
        }
    }
}