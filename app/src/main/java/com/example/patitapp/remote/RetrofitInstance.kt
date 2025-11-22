package com.example.patitapp.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Singleton Configuración de Retrofit
//Singleton es un patrón de diseño creacional que garantiza que una clase tenga una única instancia
//y proporciona un punto de acceso global a ella. Su propósito es controlar el acceso a un único objeto,
// el cual se comparte en toda la aplicación, y se usa comúnmente para gestionar la configuración, el registro o la caché
object RetrofitInstance {
    //Instanciamos el servicio de la API UNA sola vez
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}