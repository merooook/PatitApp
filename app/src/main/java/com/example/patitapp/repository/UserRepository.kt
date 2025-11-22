package com.example.patitapp.repository

import com.example.patitapp.model.UserAPI
import com.example.patitapp.remote.RetrofitInstance

//Repositorio que accede a los datos usando Retrofit
class UserRepository{
    suspend fun getUsers(): List<UserAPI>{
        return RetrofitInstance.api.getUsers()
    }
}