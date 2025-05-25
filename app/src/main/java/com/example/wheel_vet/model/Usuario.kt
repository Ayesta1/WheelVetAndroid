package com.example.wheel_vet.model

data class Usuario(
    val idusuario: Int? = null,
    val nombreusuario: String? = null,
    val telefonousuario: String? = null,
    val correousuario: String? = null,
    val direccionusuario: String? = null,
    val contrasena: String? = null,
    val tipousuario: TipoUsuario? = null,
    val ciudad: Ciudad? = null,
)
