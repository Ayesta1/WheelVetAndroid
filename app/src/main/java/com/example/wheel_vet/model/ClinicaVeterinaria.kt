package com.example.wheel_vet.model

data class ClinicaVeterinaria(
    val idclinica: Int? = null,
    val nombreclinica: String,
    val direccion: String? = null,
    val telefonoclinica: String? = null,
    val ciudad: Ciudad
)

