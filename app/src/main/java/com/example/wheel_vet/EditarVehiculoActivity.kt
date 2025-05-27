package com.example.wheel_vet

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wheel_vet.model.Vehiculo
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class EditarVehiculoActivity : AppCompatActivity() {

    private lateinit var vehiculo: Vehiculo
    private val apiService = RetrofitClient.instance

    private lateinit var editMatricula: EditText
    private lateinit var editMarca: EditText
    private lateinit var editModelo: EditText
    private lateinit var editColor: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_vehiculo)

        vehiculo = intent.getParcelableExtra("vehiculo") ?: run {
            Toast.makeText(this, "Vehículo no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        editMatricula = findViewById(R.id.editMatricula)
        editMarca = findViewById(R.id.editMarca)
        editModelo = findViewById(R.id.editModelo)
        editColor = findViewById(R.id.editColor)
        btnGuardar = findViewById(R.id.btnGuardarCambios)

        // Cargar datos en los campos
        editMatricula.setText(vehiculo.matricula)
        editMarca.setText(vehiculo.marca)
        editModelo.setText(vehiculo.modelo)
        editColor.setText(vehiculo.color)

        btnGuardar.setOnClickListener {
            guardarCambios()
        }
    }

    private fun guardarCambios() {
        val matricula = editMatricula.text.toString()
        val marca = editMarca.text.toString()
        val modelo = editModelo.text.toString()
        val color = editColor.text.toString()

        if (matricula.isBlank() || marca.isBlank() || modelo.isBlank() || color.isBlank()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val vehiculoActualizado = vehiculo.copy(
            matricula = matricula,
            marca = marca,
            modelo = modelo,
            color = color
        )

        apiService.actualizarVehiculo(vehiculo.idvehiculo!!, vehiculoActualizado).enqueue(object : Callback<Vehiculo> {
            override fun onResponse(call: Call<Vehiculo>, response: Response<Vehiculo>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarVehiculoActivity, "Vehículo actualizado", Toast.LENGTH_SHORT).show()
                    finish() // volver a la lista
                } else {
                    Toast.makeText(this@EditarVehiculoActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Vehiculo>, t: Throwable) {
                Toast.makeText(this@EditarVehiculoActivity, "Fallo en conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
