package com.example.wheel_vet.model

data class Cita(
    val idcita: Int?,
    val usuario: Usuario?,
    val mascota: Mascota?,
    val clinica: ClinicaVeterinaria?,
    val conductor: Usuario?,
    val agente: Usuario?,
    val descripcion: String?,
    val dia: String?,
    val hora: String?
)

