package com.example.patitapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.patitapp.data.MockData
import com.example.patitapp.model.Usuario
import com.example.patitapp.model.UsuarioErrores
import com.example.patitapp.model.UsuarioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUIState())

    val estado: StateFlow<UsuarioUIState> = _estado

    fun onUsuarioChange (valor : String) {
        _estado.update { it.copy(usuario = valor, errores = it.errores.copy(usuario = null)) }
    }

    fun onCorreoChange (valor : String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onPasswordChange (valor : String) {
        _estado.update { it.copy(password = valor, errores = it.errores.copy(password = null)) }
    }

    fun onConfirmPasswordChange (valor : String) {
        _estado.update { it.copy(confirmPassword = valor, errores = it.errores.copy(confirmPassword = null)) }
    }

    fun onDireccionChange (valor : String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChange(valor : Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun autenticarUsuario(): Boolean {
        val estadoActual = _estado.value
        var erroresLogin = UsuarioErrores()
        var autenticado = false

        // 1. Validaciones básicas
        if (estadoActual.usuario.isBlank()) {
            erroresLogin = erroresLogin.copy(usuario = "No puede estar vacío")
        } else if (estadoActual.usuario.length < 6) {
            erroresLogin = erroresLogin.copy(usuario = "Debe tener al menos 6 caracteres")
        }

        if (estadoActual.password.length < 8) {
            erroresLogin = erroresLogin.copy(password = "La contraseña debe tener al menos 8 caracteres")
        }

        _estado.update { it.copy(errores = erroresLogin) }

        // Si no hay errores de validación, procedemos a autenticar
        if (erroresLogin.usuario == null && erroresLogin.password == null) {
            val usuarioEncontrado = MockData.listaUsuarios.find { it.nombre == estadoActual.usuario }

            if (usuarioEncontrado == null) {
                // Usuario no existe
                _estado.update { it.copy(errores = it.errores.copy(usuario = "Usuario o contraseña incorrectos")) }
            } else if (usuarioEncontrado.password != estadoActual.password) {
                // Contraseña incorrecta
                _estado.update { it.copy(errores = it.errores.copy(usuario = "Usuario o contraseña incorrectos")) }
            } else {
                // Autenticación exitosa
                autenticado = true
            }
        }

        return autenticado
    }


    fun validarDatos(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            usuario = if (estadoActual.usuario.isBlank()) "No puede estar vacío" else if (estadoActual.usuario.length < 6) "Debe tener al menos 6 caracteres" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo Inválido" else null,
            password = if (estadoActual.password.length < 8) "La contraseña debe tener al menos 8 caracteres" else null,
            confirmPassword = if (estadoActual.password != estadoActual.confirmPassword) "Las contraseñas no coinciden" else null,
            direccion = if (estadoActual.direccion.isBlank()) "No puede estar vacío" else null
        )

        val hayErrores = listOfNotNull(
            errores.usuario,
            errores.correo,
            errores.password,
            errores.confirmPassword,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores

    }

    fun registrarUsuario(): Boolean {
        if (!validarDatos()) {
            return false
        }

        val estadoActual = _estado.value
        val usuarioExistente = MockData.listaUsuarios.find { it.nombre == estadoActual.usuario }

        if (usuarioExistente != null) {
            _estado.update { it.copy(errores = it.errores.copy(usuario = "El nombre de usuario ya existe")) }
            return false
        }

        // Add user to mock data
        val nuevoUsuario = Usuario(
            nombre = estadoActual.usuario,
            correo = estadoActual.correo,
            password = estadoActual.password,
            direccion = estadoActual.direccion
        )
        MockData.listaUsuarios.add(nuevoUsuario)

        return true
    }

}