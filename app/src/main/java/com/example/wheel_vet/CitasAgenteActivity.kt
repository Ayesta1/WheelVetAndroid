package com.example.wheel_vet

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Cita
import com.example.wheel_vet.model.UsuarioSesion
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CitasAgenteActivity : AppCompatActivity() {

    private lateinit var recyclerCitas: RecyclerView
    private val citas = mutableListOf<Cita>()
    private val apiService = RetrofitClient.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas_agente)

        recyclerCitas = findViewById(R.id.recyclerCitasAgente)
        recyclerCitas.layoutManager = LinearLayoutManager(this)
        val adapter = CitaAdapter(citas)
        recyclerCitas.adapter = adapter

        val idAgente = UsuarioSesion.usuario?.idusuario
        if (idAgente != null) {
            apiService.getCitasPorAgente(idAgente).enqueue(object : Callback<List<Cita>> {
                override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                    if (response.isSuccessful) {
                        citas.clear()
                        response.body()?.let { citas.addAll(it) }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@CitasAgenteActivity, "Error cargando citas", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                    Toast.makeText(this@CitasAgenteActivity, "Fallo en la conexión", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}
