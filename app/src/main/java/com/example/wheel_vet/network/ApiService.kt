package com.example.wheel_vet

import com.example.wheel_vet.model.AgenteVeterinario
import com.example.wheel_vet.model.Cita
import com.example.wheel_vet.model.Ciudad
import com.example.wheel_vet.model.ClinicaVeterinaria
import com.example.wheel_vet.model.Conductor
import com.example.wheel_vet.model.LoginRequest
import com.example.wheel_vet.model.Mascota
import com.example.wheel_vet.model.Usuario
import com.example.wheel_vet.model.Vehiculo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {

    //Endpoints Ciudades

    @GET("ciudades")
    fun getCiudades(): Call<List<Ciudad>>

    //Endpoints Usuarios

    @POST("usuarios")
    fun registrarUsuario(@Body usuario: Usuario): Call<Void>

    @POST("login")
    fun loginUsuario(@Body request: LoginRequest): Call<Usuario>

    @PUT("usuarios/{id}")
    fun actualizarUsuario(@Path("id") id: Int?, @Body usuario: Usuario): Call<Usuario>

    //Endpoints Clinicas

    @GET("clinicas/ciudad/{idciudad}")
    fun getClinicasPorCiudad(@Path("idciudad") idCiudad: Int): Call<List<ClinicaVeterinaria>>

    //Endpoints Citas



    @GET("citas/usuario/{id}")
    fun getCitasPorUsuario(@Path("id") idusuario: Int): Call<List<Cita>>

    @GET("citas/mascota/{idMascota}")
    fun getCitasPorMascota(@Path("idMascota") idMascota: Int): Call<List<Cita>>

    @GET("citas/conductor/{idconductor}")
    fun getCitasPorConductor(@Path("idconductor") idconductor: Int): Call<List<Cita>>

    @GET("citas/agente/{idagente}")
    fun getCitasPorAgente(@Path("idagente") idAgente: Int): Call<List<Cita>>

    @POST("citas")
    fun crearCita(@Body cita: Cita): Call<Cita>

    @GET("citas") fun getCitas(): Call<List<Cita>>

    //Endpoints Mascotas

    @GET("mascotas/usuario/{idusuario}")
    fun getMascotasPorUsuario(@Path("idusuario") idusuario: Int): Call<List<Mascota>>

    @GET("mascotas/tratadas/usuario/{id}")
    fun getMascotasTratadasPorUsuario(@Path("id") idUsuario: Int): Call<List<Mascota>>

    @POST("mascotas")
    fun crearMascota(@Body mascota: Mascota): Call<Mascota>

    @DELETE("mascotas/{idmascota}")
    fun eliminarMascota(@Path("idmascota") idmascota: Int): Call<Void>

    //Enpoints Conductores

    @GET("conductores")
    fun getConductores(): Call<List<Conductor>>

    //Endpoints Agentes

    @GET("agentes")
    fun getAgentesVeterinarios(): Call<List<AgenteVeterinario>>

    //Endpoints Vehiculo

    @PUT("vehiculos/{id}")
    fun actualizarVehiculo(@Path("id") id: Int, @Body vehiculo: Vehiculo): Call<Vehiculo>

    @POST("vehiculos")
    fun crearVehiculo(@Body vehiculo: Vehiculo): Call<Vehiculo>

    @GET("vehiculos/conductor/{id}")
    fun getVehiculosPorConductor(@Path("id") idConductor: Int): Call<List<Vehiculo>>

    @DELETE("vehiculos/{id}")
    fun eliminarVehiculo(@Path("id") idVehiculo: Int): Call<Void>









}
