package com.example.patitapp.network

import com.example.patitapp.model.Service
import com.example.patitapp.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // Users
    @GET("/api/users")
    suspend fun getAllUsers(): List<Usuario>

    @POST("/api/users")
    suspend fun createUser(@Body user: Usuario): Usuario

    // Locations
    @GET("/api/locations")
    suspend fun getAllLocations(): List<Service>

    @POST("/api/locations")
    suspend fun createLocation(@Body location: Service): Service
}
