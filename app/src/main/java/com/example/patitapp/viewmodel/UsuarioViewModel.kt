package com.example.patitapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patitapp.model.Usuario
import com.example.patitapp.model.UsuarioErrores
import com.example.patitapp.model.UsuarioUIState
import com.example.patitapp.network.RetrofitClient
import com.example.patitapp.utils.Validations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUIState())
    val estado: StateFlow<UsuarioUIState> = _estado.asStateFlow()

    // Variable para comunicar el resultado del Login a la UI
    // null = nada, true = éxito, false = error
    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult: StateFlow<Boolean?> = _loginResult.asStateFlow()

    // --- FUNCIONES DE CAMBIO DE ESTADO (LOGIN) ---
    fun onCorreoChange(valor: String) {
        _estado.update { st ->
            st.copy(
                correo = valor,
                errores = st.errores.copy(correo = null)
            ).recalcularPuedeIngresarLogin()
        }
    }

    fun onPasswordChange(valor: String) {
        _estado.update { st ->
            st.copy(
                password = valor,
                errores = st.errores.copy(password = null)
            ).recalcularPuedeIngresarLogin()
        }
    }

    // --- FUNCIONES DE CAMBIO DE ESTADO (REGISTRO) ---
    fun onUsuarioChange(valor: String) = _estado.update {
        it.copy(usuario = valor, errores = it.errores.copy(usuario = null))
    }

    fun onConfirmPasswordChange(valor: String) = _estado.update {
        it.copy(confirmPassword = valor, errores = it.errores.copy(confirmPassword = null))
    }

    fun onDireccionChange(valor: String) = _estado.update {
        it.copy(direccion = valor, errores = it.errores.copy(direccion = null))
    }

    fun onAceptarTerminosChange(valor: Boolean) = _estado.update {
        it.copy(aceptaTerminos = valor)
    }

    // --- AUTENTICACIÓN (LOGIN) ---
    fun autenticarUsuario() {
        val st = _estado.value

        // 1. Validaciones locales rápidas
        val errorCorreo = if (st.correo.isBlank()) "Ingresa correo" else if (!Validations.esEmail(st.correo)) "Email inválido" else null
        val errorPass = if (st.password.isBlank()) "Ingresa contraseña" else null

        if (errorCorreo != null || errorPass != null) {
            _estado.update { it.copy(errores = it.errores.copy(correo = errorCorreo, password = errorPass)) }
            return
        }

        // 2. Llamada a la API (Asíncrona)
        viewModelScope.launch {
            try {
                // Asegúrate que en RetrofitClient se llame 'apiService'
                val users = RetrofitClient.apiService.getAllUsers()

                val usuarioEncontrado = users.find {
                    it.correo.equals(st.correo, ignoreCase = true) && it.password == st.password
                }

                if (usuarioEncontrado != null) {
                    println("Login exitoso: ${usuarioEncontrado.nombre}")
                    _loginResult.value = true // ¡AVISAMOS A LA UI QUE FUE UN ÉXITO!
                } else {
                    _estado.update {
                        it.copy(errores = it.errores.copy(correo = "Credenciales incorrectas"))
                    }
                    _loginResult.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _estado.update {
                    it.copy(errores = it.errores.copy(correo = "Error de conexión"))
                }
                _loginResult.value = false
            }
        }
    }

    // --- REGISTRO ---
    fun registrarUsuario() {
        if (!validarDatosRegistro()) return
        val st = _estado.value

        viewModelScope.launch {
            try {
                val nuevoUsuario = Usuario(
                    // id se genera en base de datos, puedes mandar 0 o null si tu modelo lo permite
                    id = 0,
                    nombre = st.usuario,
                    correo = st.correo,
                    password = st.password,
                    direccion = st.direccion
                )

                RetrofitClient.apiService.createUser(nuevoUsuario)
                println("Usuario registrado correctamente")
                _loginResult.value = true // Asumimos que al registrarse entra directo

            } catch (e: Exception) {
                e.printStackTrace()
                _estado.update { it.copy(errores = it.errores.copy(usuario = "Error al registrar: ${e.message}")) }
            }
        }
    }

    // --- VALIDACIONES ---
    private fun UsuarioUIState.recalcularPuedeIngresarLogin(): UsuarioUIState {
        val okCorreo = correo.isNotBlank()
        val okPass = password.isNotBlank()
        return copy(puedeIngresar = okCorreo && okPass)
    }

    private fun validarDatosRegistro(): Boolean {
        val st = _estado.value
        var valido = true
        var errores = UsuarioErrores()

        if (st.usuario.isBlank()) { errores = errores.copy(usuario = "Nombre requerido"); valido = false }
        if (!Validations.esEmail(st.correo)) { errores = errores.copy(correo = "Email inválido"); valido = false }
        if (!Validations.esPasswordFuerte(st.password)) { errores = errores.copy(password = "Mín 8 caracteres"); valido = false }
        if (st.password != st.confirmPassword) { errores = errores.copy(confirmPassword = "No coinciden"); valido = false }
        if (!st.aceptaTerminos) { valido = false } // Podrías mostrar un toast en la UI

        _estado.update { it.copy(errores = errores) }
        return valido
    }

    // Limpiar estado al salir
    fun resetLoginState() {
        _loginResult.value = null
    }
}