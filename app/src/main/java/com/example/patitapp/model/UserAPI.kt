package com.example.patitapp.model

//POST para la API
data class UserAPI(
    val userId: Int, //ID del usuario que creó el post
    val nombre: String, //Nombre del usuario que creó el post
    val correo: String, //Correo del usuario que creó el post
    val password: String, //Contraseña del usuario que creó el post
    val direccion: String //Dirección del usuario que creó el post
)
