package com.example.wheel_vet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(
    val idusuario: Int,
    val nombreusuario: String? = null,
    val telefonousuario: String? = null,
    val correousuario: String? = null,
    val direccionusuario: String? = null,
    val contrasena: String? = null,
    val tipousuario: TipoUsuario? = null,
    val ciudad: Ciudad? = null,
) : Parcelable
