package com.example.wheel_vet.model

data class AgenteVeterinario(
    val idusuario: Int,
    val clinica: ClinicaVeterinaria? = null,
    val usuario: Usuario? = null
)
