package com.example.patitapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.patitapp.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.patitapp.model.UserAPI
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel: ViewModel(){
    private val repository = UserRepository()

    //flujo mutable que contiene la lista de users
    private val _userList= MutableStateFlow<List<UserAPI>>(emptyList())

    //Flujo público solo de lectura
    val userList: StateFlow<List<UserAPI>> = _userList
    //se llama automáticamente al iniciar
    init{
        fetchUsers()
    }
    //Función que obtiene los datos en segundo plano
    //La aplicación se conecta a internet para descargar o sincronizar información
    //mientras no está abierta o en uso activo
    private fun fetchUsers(){
        viewModelScope.launch {
            try{
                _userList.value = repository.getUsers()
            } catch (e: Exception){
                //Manejo de errores
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }
    }
}