package com.example.wheel_vet

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wheel_vet.model.UsuarioSesion
import com.example.wheel_vet.model.Vehiculo
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class AnadirVehiculoActivity : AppCompatActivity() {

    private lateinit var edMatricula: EditText
    private lateinit var edMarca: EditText
    private lateinit var edModelo: EditText
    private lateinit var edColor: EditText
    private lateinit var btnGuardar: Button
    private val apiService = RetrofitClient.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_vehiculo)

        edMatricula = findViewById(R.id.edMatricula)
        edMarca = findViewById(R.id.edMarca)
        edModelo = findViewById(R.id.edModelo)
        edColor = findViewById(R.id.edColor)
        btnGuardar = findViewById(R.id.btnGuardarVehiculo)

        btnGuardar.setOnClickListener {
            guardarVehiculo()
        }
    }

    private fun guardarVehiculo() {
        val matricula = edMatricula.text.toString()
        val marca = edMarca.text.toString()
        val modelo = edModelo.text.toString()
        val color = edColor.text.toString()
        val usuario = UsuarioSesion.usuario

        if (usuario != null) {
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
                        Toast.makeText(this@AnadirVehiculoActivity, "Vehículo añadido", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AnadirVehiculoActivity, "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Vehiculo>, t: Throwable) {
                    Toast.makeText(this@AnadirVehiculoActivity, "Fallo en conexión", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "No hay usuario en sesión", Toast.LENGTH_SHORT).show()
        }
    }
}
