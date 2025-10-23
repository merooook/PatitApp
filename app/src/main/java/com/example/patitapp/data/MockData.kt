package com.example.patitapp.data

import com.example.patitapp.R
import com.example.patitapp.model.Service
import com.example.patitapp.model.Usuario

// Fuente de datos MOCK (sin backend) para la lista.

object MockData {
    val listaUsuarios = mutableListOf(
        Usuario(
            nombre = "testuser",
            correo = "test@test.com",
            password = "password123",
            direccion = "Calle Falsa 123"
        )
    )

    val services = listOf(
        Service(
            id = 1,
            name = "Veterinaria 24h Viña",
            description = "Urgencias 24/7",
            priceCLP = 0,
            imageRes = R.drawable.urgencia,
            lat = -33.024, lon = -71.551,
            category = "vet24"
        ),
        Service(
            id = 2,
            name = "Baño perro",
            description = "Baño básico perro mediano",
            priceCLP = 12990,
            imageRes = R.drawable.bano,
            lat = -33.031, lon = -71.545,
            category = "servicio"
        ),
        Service(
            id = 3,
            name = "Corte de Uñas",
            description = "Incluye lavado de oídos",
            priceCLP = 3990,
            imageRes = R.drawable.corteuna,
            lat = -33.045, lon = -71.615,
            category = "servicio"
        ),
        Service(
            id = 4,
            name = "PetZonas",
            description = "Alimento y accesorios",
            priceCLP = 0,
            imageRes = R.drawable.petzonas,
            lat = -33.378, lon = -71.549,
            category = "tienda"
        )
    )
}