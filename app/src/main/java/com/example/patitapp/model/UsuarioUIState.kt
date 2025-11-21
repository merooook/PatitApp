package com.example.patitapp.model

data class UsuarioUIState(
    val usuario : String = "",          // usado en signin
    val correo : String = "",           // usado en login/signin
    val password : String = "",
    val confirmPassword : String = "",
    val direccion : String = "",
    val aceptaTerminos : Boolean = false,
    val errores : UsuarioErrores = UsuarioErrores(),
    val puedeIngresar: Boolean = false  // habilita el botón Login
)
