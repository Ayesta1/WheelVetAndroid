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

class MascotaTratadaActivity : AppCompatActivity() {

    private lateinit var recyclerMascotas: RecyclerView
    private val citas = mutableListOf<Cita>()
    private val apiService = RetrofitClient.instance
    private lateinit var adapter: MascotaTratadaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota_tratada)

        recyclerMascotas = findViewById(R.id.recyclerMascotasTratadas)
        recyclerMascotas.layoutManager = LinearLayoutManager(this)
        adapter = MascotaTratadaAdapter(citas)
        recyclerMascotas.adapter = adapter

        val idUsuario = UsuarioSesion.usuario?.idusuario

        if (idUsuario != null) {
            apiService.getCitasPorUsuario(idUsuario).enqueue(object : Callback<List<Cita>> {
                override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                    if (response.isSuccessful) {
                        citas.clear()
                        response.body()?.let { citas.addAll(it) }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@MascotaTratadaActivity, "Error cargando citas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                    Toast.makeText(this@MascotaTratadaActivity, "Fallo en la conexión", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}





