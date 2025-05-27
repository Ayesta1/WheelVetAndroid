package com.example.wheel_vet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vehiculo(
    val idvehiculo: Int?,
    val matricula: String,
    val marca: String,
    val modelo: String,
    val color: String,
    val conductor: Usuario
) : Parcelable

