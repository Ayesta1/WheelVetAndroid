package com.example.wheel_vet

import com.example.wheel_vet.model.Ciudad
import com.example.wheel_vet.model.LoginRequest
import com.example.wheel_vet.model.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body


interface ApiService {
    @GET("ciudades")
    fun getCiudades(): Call<List<Ciudad>>

    @POST("usuarios")
    fun registrarUsuario(@Body usuario: Usuario): Call<Void>

    @POST("login")
    fun loginUsuario(@Body request: LoginRequest): Call<Usuario>

}
