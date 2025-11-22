package com.example.patitapp.remote

import com.example.patitapp.model.UserAPI
import retrofit2.http.GET

//Interfaz que define los endpoints de HTTP
interface ApiService{
    @GET("/users")
    suspend fun getUsers(): List<UserAPI>
}