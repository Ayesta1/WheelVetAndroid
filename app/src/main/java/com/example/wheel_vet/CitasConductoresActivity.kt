package com.example.wheel_vet

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wheel_vet.model.Cita
import com.example.wheel_vet.model.UsuarioSesion
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitasConductoresActivity : AppCompatActivity() {

    private val apiService = RetrofitClient.instance
    private lateinit var listView: ListView
    private val citas = mutableListOf<Cita>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas_conductores)

        listView = findViewById(R.id.listViewCitasConductores)

        val idConductor = UsuarioSesion.usuario?.idusuario ?: return

        apiService.getCitasPorConductor(idConductor).enqueue(object : Callback<List<Cita>> {
            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                if (response.isSuccessful) {
                    citas.clear()
                    response.body()?.let { citas.addAll(it) }
                    mostrarCitas()
                } else {
                    Toast.makeText(this@CitasConductoresActivity, "Error al cargar las citas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                Toast.makeText(this@CitasConductoresActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarCitas() {
        val datos = citas.map {
            val mascota = it.mascota?.nombremascota ?: "Sin mascota"
            val descripcion = it.descripcion ?: "Sin descripción"
            val direccion = it.usuario?.direccionusuario
            val fecha = it.dia.toString()
            val hora = it.hora.toString()
            "$mascota\n$fecha $hora\n$descripcion\n$direccion"
        }

        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, datos)
    }
}
