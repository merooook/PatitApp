package com.example.patitapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.patitapp.data.MockData
import com.example.patitapp.model.Usuario
import com.example.patitapp.model.UsuarioErrores
import com.example.patitapp.model.UsuarioUIState
import com.example.patitapp.utils.Validations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUIState())
    val estado: StateFlow<UsuarioUIState> = _estado

    // LOGIN --> correo + password
    fun onCorreoChange(valor: String) {
        _estado.update { st ->
            val st2 = st.copy(correo = valor, errores = st.errores.copy(correo = null))
            st2.recalcularPuedeIngresarLogin()
        }
    }

    fun onPasswordChange(valor: String) {
        _estado.update { st ->
            val st2 = st.copy(password = valor, errores = st.errores.copy(password = null))
            st2.recalcularPuedeIngresarLogin()
        }
    }

    // SIGNIN
    fun onUsuarioChange(valor: String) {
        _estado.update { it.copy(usuario = valor, errores = it.errores.copy(usuario = null)) }
    }

    fun onConfirmPasswordChange(valor: String) {
        _estado.update { it.copy(confirmPassword = valor, errores = it.errores.copy(confirmPassword = null)) }
    }

    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    // Autenticación requiere correo válido + contraseña fuerte
    // Busca por correo y si no existe intenta por nombre para compatibilidad

    fun autenticarUsuario(): Boolean {
        val st = _estado.value
        var errs = UsuarioErrores()

        // Validaciones de formato: correo y contraseña
        errs = errs.copy(
            correo = when {
                st.correo.isBlank() -> "Ingresa tu correo"
                !com.example.patitapp.utils.Validations.esEmail(st.correo) -> "Formato de correo inválido"
                else -> null
            },
            password = when {
                st.password.isBlank() -> "Ingresa tu contraseña"
                !com.example.patitapp.utils.Validations.esPasswordFuerte(st.password) -> "Mín. 8 con letras y números"
                else -> null
            }
        )

        // Actualizamos errores para que la UI los muestre si corresponde
        _estado.update { it.copy(errores = errs) }

        // Si hay errores de formato, no seguimos
        val sinErrores = errs.correo == null && errs.password == null
        if (!sinErrores) return false

        // Buscamos usuario por correo (preferido) o por nombre (compatibilidad con versiones previas)
        var usuarioEncontrado = com.example.patitapp.data.MockData.listaUsuarios.find {
            it.correo.equals(st.correo, ignoreCase = true)
        }
        if (usuarioEncontrado == null) {
            usuarioEncontrado = com.example.patitapp.data.MockData.listaUsuarios.find {
                it.nombre.equals(st.correo, ignoreCase = true)
            }
        }

        // Si no existe, creamos el usuario con datos mínimos para permitir demostracion
        // Nombre: parte antes de '@' si existe, o el correo completo como fallback

        if (usuarioEncontrado == null) {
            val nombreGenerado = st.correo.substringBefore('@').ifBlank { st.correo }
            val nuevoUsuario = com.example.patitapp.model.Usuario(
                nombre = nombreGenerado,
                correo = st.correo,
                password = st.password,
                direccion = "" // campo opcional por ahora
            )
            com.example.patitapp.data.MockData.listaUsuarios.add(nuevoUsuario)
            // Autenticación satisfactoria (acabamos de crear el usuario con esa contraseña)
            return true
        }

        // Si existe, comprobamos la contraseña como antes
        if (usuarioEncontrado.password != st.password) {
            _estado.update {
                it.copy(errores = it.errores.copy(correo = "Correo o contraseña incorrectos"))
            }
            return false
        }

        // Si todos coinciden --> autenticacion exitosa
        return true
    }

    // Registro con validaciones mejoradas
    fun validarDatos(): Boolean {
        val st = _estado.value
        val errs = UsuarioErrores(
            usuario = if (st.usuario.isBlank()) "No puede estar vacío" else if (st.usuario.length < 6) "Debe tener al menos 6 caracteres" else null,
            correo = when {
                st.correo.isBlank() -> "No puede estar vacío"
                !Validations.esEmail(st.correo) -> "Correo inválido"
                else -> null
            },
            password = if (!Validations.esPasswordFuerte(st.password)) "Mín. 8 con letras y números" else null,
            confirmPassword = if (st.password != st.confirmPassword) "Las contraseñas no coinciden" else null,
            direccion = if (st.direccion.isBlank()) "No puede estar vacío" else null
        )

        _estado.update { it.copy(errores = errs) }
        val hayErrores = listOfNotNull(
            errs.usuario, errs.correo, errs.password, errs.confirmPassword, errs.direccion
        ).isNotEmpty()
        return !hayErrores
    }

    fun registrarUsuario(): Boolean {
        if (!validarDatos()) return false
        val st = _estado.value

        val usuarioExistente = MockData.listaUsuarios.find {
            it.nombre.equals(st.usuario, ignoreCase = true) || it.correo.equals(st.correo, ignoreCase = true)
        }
        if (usuarioExistente != null) {
            _estado.update { it.copy(errores = it.errores.copy(usuario = "El usuario/correo ya existe")) }
            return false
        }

        val nuevoUsuario = Usuario(
            nombre = st.usuario,
            correo = st.correo,
            password = st.password,
            direccion = st.direccion
        )
        MockData.listaUsuarios.add(nuevoUsuario)
        return true
    }

    // HELPERS
    // Antes: validaba email+contraseña fuerte para habilitar el botón
    // Ahora: solo pide campos no vacíos; la validación estricta queda para autenticarUsuario()
    private fun UsuarioUIState.recalcularPuedeIngresarLogin(): UsuarioUIState {
        val okCorreo = correo.isNotBlank()
        val okPass = password.isNotBlank()
        return copy(puedeIngresar = okCorreo && okPass)
    }

}
