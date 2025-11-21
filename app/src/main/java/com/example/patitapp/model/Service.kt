package com.example.patitapp.model

// Modelo simple de un servicio que muestra la lista.
// imageRes apunta a un drawable local

data class Service(
    val id: Int,
    val name: String,
    val description: String,
    val priceCLP: Int,     // 0 si no aplica
    val imageRes: Int,     // recurso drawable (png/xml en res/drawable)
    val lat: Double,       // latitud del servicio (mock)
    val lon: Double,       // longitud del servicio (mock)
    val category: String   // "vet24" | "servicio" | "tienda"
)
