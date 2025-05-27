package com.example.wheel_vet

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.wheel_vet.model.UsuarioSesion
import com.example.wheel_vet.model.Usuario
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etContrasena: EditText
    private lateinit var tvTipoUsuario: TextView
    private lateinit var tvCiudad: TextView
    private lateinit var btnGuardar: Button

    private val apiService = RetrofitClient.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etTelefono = findViewById(R.id.etTelefono)
        etDireccion = findViewById(R.id.etDireccion)
        etContrasena = findViewById(R.id.etContrasena)
        tvTipoUsuario = findViewById(R.id.tvTipoUsuario)
        tvCiudad = findViewById(R.id.tvCiudad)
        btnGuardar = findViewById(R.id.btnGuardar)

        val usuario = UsuarioSesion.usuario
        if (usuario != null) {
            etNombre.setText(usuario.nombreusuario)
            etCorreo.setText(usuario.correousuario)
            etTelefono.setText(usuario.telefonousuario)
            etDireccion.setText(usuario.direccionusuario)
            etContrasena.setText(usuario.contrasena)
            tvTipoUsuario.text = "Tipo de usuario: ${usuario.tipousuario}"
            tvCiudad.text = "Ciudad: ${usuario.ciudad?.nombreciudad}"
        }

        btnGuardar.setOnClickListener {
            val usuarioActualizado = usuario?.copy(
                nombreusuario = etNombre.text.toString(),
                correousuario = etCorreo.text.toString(),
                telefonousuario = etTelefono.text.toString(),
                direccionusuario = etDireccion.text.toString(),
                contrasena = etContrasena.text.toString()
            )

            if (usuarioActualizado != null) {
                apiService.actualizarUsuario(usuarioActualizado.idusuario, usuarioActualizado)
                    .enqueue(object : Callback<Usuario> {
                        override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@PerfilActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                                UsuarioSesion.usuario = response.body() // actualizamos la sesión
                            } else {
                                Toast.makeText(this@PerfilActivity, "Error al guardar", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Usuario>, t: Throwable) {
                            Toast.makeText(this@PerfilActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }
}
