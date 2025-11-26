package com.example.patitapp.model

data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val correo: String,
    val password: String,
    val direccion: String
)
