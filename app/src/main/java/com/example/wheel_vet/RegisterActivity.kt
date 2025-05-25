package com.example.wheel_vet

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.wheel_vet.model.Ciudad
import com.example.wheel_vet.model.TipoUsuario
import com.example.wheel_vet.model.Usuario
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etContrasena: EditText
    private lateinit var spinnerTipoUsuario: Spinner
    private lateinit var spinnerCiudad: Spinner
    private lateinit var btnRegistrar: Button

    private lateinit var api: ApiService
    private var ciudades: List<Ciudad> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre)
        etTelefono = findViewById(R.id.etTelefono)
        etCorreo = findViewById(R.id.etCorreo)
        etDireccion = findViewById(R.id.etDireccion)
        etContrasena = findViewById(R.id.etContrasena)
        spinnerTipoUsuario = findViewById(R.id.spinnerTipoUsuario)
        spinnerCiudad = findViewById(R.id.spinnerCiudad)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        // Retrofit setup
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.18:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)

        configurarTipoUsuario()
        cargarCiudadesDesdeBackend()

        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun configurarTipoUsuario() {
        val tipos = TipoUsuario.values().map { it.name.lowercase().replaceFirstChar(Char::uppercase) }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipoUsuario.adapter = adapter
    }

    private fun cargarCiudadesDesdeBackend() {
        api.getCiudades().enqueue(object : Callback<List<Ciudad>> {
            override fun onResponse(call: Call<List<Ciudad>>, response: Response<List<Ciudad>>) {
                if (response.isSuccessful) {
                    ciudades = response.body() ?: listOf()
                    val nombres = ciudades.map { it.nombreciudad }
                    val adapter = ArrayAdapter(this@RegisterActivity, android.R.layout.simple_spinner_item, nombres)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerCiudad.adapter = adapter
                } else {
                    Toast.makeText(this@RegisterActivity, "Error al cargar ciudades", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Ciudad>>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registrarUsuario() {
        if (ciudades.isEmpty()) {
            Toast.makeText(this, "Debe cargar las ciudades primero", Toast.LENGTH_SHORT).show()
            return
        }

        val usuario = Usuario(
            nombreusuario = etNombre.text.toString(),
            telefonousuario = etTelefono.text.toString(),
            correousuario = etCorreo.text.toString(),
            direccionusuario = etDireccion.text.toString(),
            contrasena = etContrasena.text.toString(),
            tipousuario = TipoUsuario.valueOf(spinnerTipoUsuario.selectedItem.toString().uppercase()),
            ciudad = ciudades[spinnerCiudad.selectedItemPosition]
        )

        api.registrarUsuario(usuario).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
