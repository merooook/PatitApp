package com.example.patitapp.model

data class UsuarioErrores(
    val usuario: String? = null,
    val correo: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val direccion: String? = null
    //A agregar rut, ID
)