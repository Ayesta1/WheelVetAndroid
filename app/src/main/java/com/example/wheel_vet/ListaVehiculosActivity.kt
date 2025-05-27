package com.example.wheel_vet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.UsuarioSesion
import com.example.wheel_vet.model.Vehiculo
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaVehiculosActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: VehiculoAdapter
    private val vehiculos = mutableListOf<Vehiculo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_vehiculos)

        recycler = findViewById(R.id.recyclerVehiculos)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = VehiculoAdapter(vehiculos, ::editarVehiculo, ::eliminarVehiculo)
        recycler.adapter = adapter

        cargarVehiculos()
    }

    private fun cargarVehiculos() {
        val idConductor = UsuarioSesion.usuario?.idusuario ?: return
        RetrofitClient.instance.getVehiculosPorConductor(idConductor).enqueue(object : Callback<List<Vehiculo>> {
            override fun onResponse(call: Call<List<Vehiculo>>, response: Response<List<Vehiculo>>) {
                if (response.isSuccessful) {
                    vehiculos.clear()
                    response.body()?.let { vehiculos.addAll(it) }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@ListaVehiculosActivity, "Error al cargar vehículos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Vehiculo>>, t: Throwable) {
                Toast.makeText(this@ListaVehiculosActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editarVehiculo(vehiculo: Vehiculo) {
        val intent = Intent(this, EditarVehiculoActivity::class.java)
        intent.putExtra("vehiculo", vehiculo)
        startActivity(intent)
    }

    private fun eliminarVehiculo(vehiculo: Vehiculo) {
        RetrofitClient.instance.eliminarVehiculo(vehiculo.idvehiculo!!).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    vehiculos.remove(vehiculo)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@ListaVehiculosActivity, "Vehículo eliminado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ListaVehiculosActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ListaVehiculosActivity, "Fallo de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

