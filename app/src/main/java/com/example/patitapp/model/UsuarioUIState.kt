package com.example.patitapp.model

data class UsuarioUIState(
    val usuario : String = "",
    val correo : String = "",
    val password : String = "",
    val confirmPassword : String = "",
    val direccion : String = "",
    val aceptaTerminos : Boolean = false,
    val errores : UsuarioErrores = UsuarioErrores()
)