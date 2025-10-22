package com.example.patitapp.data

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

data class Usuario(val correo: String, val password: String, val direccion: String)

class AppState (private val dataStore: DataStoreManager) {
    val usuarios = mutableStateListOf<Usuario>()
    var usuarioActual: Usuario? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    fun cargarDatos(){
        scope.launch{
            val users = dataStore.getUsers().first()

            usuarios.clear()
            usuarios.addAll(users)
        }
    }

    fun registrarUsuario(correo: String, password: String, direccion: String): Boolean{
        if (usuarios.any{it.correo == correo}) return false
        val nuevo = Usuario(correo, password, direccion)
        usuarios.add(nuevo)
        guardarUsuarios()
        return true
    }
    fun guardarUsuarios(){
        scope.launch {
            dataStore.saveUsers(usuarios)
        }
    }

    fun login(correo: String, password: String, direccion: String): Boolean{
        var user = usuarios.find {it.correo == correo && it.password == password && it.direccion == direccion}
        return if (user != null){
            usuarioActual = user
            true
        }else false
    }

    fun logout(){
        usuarioActual = null
    }
}

