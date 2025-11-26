package com.example.patitapp.model

import com.google.gson.annotations.SerializedName

data class Service(
    val id: Int,
    val name: String,
    val description: String,


    @SerializedName("priceCLP")
    val priceCLP: Int,

    val imageRes: Int = 0,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    val lat: Double,
    val lon: Double,
    val category: String
)