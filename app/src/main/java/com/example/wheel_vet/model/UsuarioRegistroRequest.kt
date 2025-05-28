package com.example.wheel_vet.model

data class UsuarioRegistroRequest(
    val nombreusuario: String,
    val telefonousuario: String,
    val correousuario: String,
    val direccionusuario: String,
    val contrasena: String,
    val tipousuario: TipoUsuario,
    val ciudad: Ciudad
)
