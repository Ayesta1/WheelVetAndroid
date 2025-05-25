package com.example.wheel_vet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wheel_vet.model.LoginRequest
import com.example.wheel_vet.model.Usuario
import com.example.wheel_vet.model.TipoUsuario
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etCorreousuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnLogin: Button

    private val apiService = RetrofitClient.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etCorreousuario = findViewById(R.id.etCorreousuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val correousuario = etCorreousuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (correousuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            hacerLogin(correousuario, contrasena)
        }
    }

    private fun hacerLogin(correousuario: String, contrasena: String) {
        val request = LoginRequest(correousuario, contrasena)
        val call = apiService.loginUsuario(request)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        manejarLoginExitoso(usuario)
                    } else {
                        Toast.makeText(this@LoginActivity, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun manejarLoginExitoso(usuario: Usuario) {
        when (usuario.tipousuario) {
            TipoUsuario.GENERAL -> {
                Toast.makeText(this, "Bienvenido General: ${usuario.nombreusuario}", Toast.LENGTH_LONG).show()
                val intent = Intent(this, GeneralActivity::class.java)
                startActivity(intent)
            }
            TipoUsuario.CONDUCTOR -> {
                Toast.makeText(this, "Bienvenido Conductor: ${usuario.nombreusuario}", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ConductorActivity::class.java))
            }
            TipoUsuario.CLINICA -> {
                Toast.makeText(this, "Bienvenido Clínica: ${usuario.nombreusuario}", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ClinicaActivity::class.java))
            }
            TipoUsuario.AGENTE -> {
                Toast.makeText(this, "Bienvenido Agente: ${usuario.nombreusuario}", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, AgenteActivity::class.java))
            }
            else -> {
                Toast.makeText(this, "Tipo de usuario desconocido", Toast.LENGTH_SHORT).show()
            }
        }
        finish() // Cierra LoginActivity para que no vuelva al presionar atrás
    }
}
