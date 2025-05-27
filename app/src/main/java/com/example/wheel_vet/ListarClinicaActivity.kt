package com.example.wheel_vet

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Ciudad
import com.example.wheel_vet.model.ClinicaVeterinaria
import com.example.wheel_vet.model.UsuarioSesion
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListarClinicaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClinicaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listarclinicas)

        recyclerView = findViewById(R.id.rvClinicas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val ciudadId = UsuarioSesion.usuario?.ciudad?.idciudad
        if (ciudadId != null) {
            RetrofitClient.instance.getClinicasPorCiudad(ciudadId)
                .enqueue(object : Callback<List<ClinicaVeterinaria>> {
                    override fun onResponse(
                        call: Call<List<ClinicaVeterinaria>>,
                        response: Response<List<ClinicaVeterinaria>>
                    ) {
                        if (response.isSuccessful) {
                            val clinicas = response.body() ?: emptyList()
                            adapter = ClinicaAdapter(clinicas)
                            recyclerView.adapter = adapter
                        } else {
                            Toast.makeText(this@ListarClinicaActivity, "Error al cargar clínicas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<ClinicaVeterinaria>>, t: Throwable) {
                        Toast.makeText(this@ListarClinicaActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "No se encontró la ciudad del usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
