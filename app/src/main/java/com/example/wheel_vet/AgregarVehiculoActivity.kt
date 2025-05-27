package com.example.wheel_vet

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.wheel_vet.model.UsuarioSesion
import com.example.wheel_vet.model.Usuario
import com.example.wheel_vet.model.Vehiculo
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarVehiculoActivity : AppCompatActivity() {

    private lateinit var editMatricula: EditText
    private lateinit var editMarca: EditText
    private lateinit var editModelo: EditText
    private lateinit var editColor: EditText
    private lateinit var btnRegistrar: Button

    private val apiService = RetrofitClient.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_vehiculo)

        editMatricula = findViewById(R.id.editMatricula)
        editMarca = findViewById(R.id.editMarca)
        editModelo = findViewById(R.id.editModelo)
        editColor = findViewById(R.id.editColor)
        btnRegistrar = findViewById(R.id.btnRegistrarVehiculo)

        btnRegistrar.setOnClickListener {
            registrarVehiculo()
        }
    }

    private fun registrarVehiculo() {
        val matricula = editMatricula.text.toString()
        val marca = editMarca.text.toString()
        val modelo = editModelo.text.toString()
        val color = editColor.text.toString()

        if (matricula.isBlank() || marca.isBlank() || modelo.isBlank() || color.isBlank()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val usuario = UsuarioSesion.usuario
        if (usuario == null) {
            Toast.makeText(this, "Usuario no válido", Toast.LENGTH_SHORT).show()
            return
        }

        val vehiculo = Vehiculo(
            idvehiculo = null,
            matricula = matricula,
            marca = marca,
            modelo = modelo,
            color = color,
            conductor = usuario
        )

        apiService.crearVehiculo(vehiculo).enqueue(object : Callback<Vehiculo> {
            override fun onResponse(call: Call<Vehiculo>, response: Response<Vehiculo>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AgregarVehiculoActivity, "Vehículo registrado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AgregarVehiculoActivity, "Error al registrar vehículo", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Vehiculo>, t: Throwable) {
                Toast.makeText(this@AgregarVehiculoActivity, "Fallo en conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
