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

class MascotaTratadaActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val api = RetrofitClient.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota_tratada)

        listView = findViewById(R.id.listaMascotasTratadas)

        val agenteId = UsuarioSesion.usuario?.idusuario ?: return

        api.getCitasPorAgente(agenteId).enqueue(object : Callback<List<Cita>> {
            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                if (response.isSuccessful) {
                    val citas = response.body() ?: emptyList()
                    val datos = citas.map {
                        val mascotaNombre = it.mascota?.nombremascota ?: "Sin nombre"
                        val fecha = it.dia.toString()
                        val hora = it.hora.toString()
                        val descripcion = it.descripcion ?: "Sin descripción"

                        "$mascotaNombre\n$fecha $hora\n$descripcion"
                    }


                    listView.adapter = ArrayAdapter(
                        this@MascotaTratadaActivity,
                        android.R.layout.simple_list_item_1,
                        datos
                    )
                } else {
                    Toast.makeText(this@MascotaTratadaActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                Toast.makeText(this@MascotaTratadaActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }
}






