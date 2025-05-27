package com.example.wheel_vet

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Cita
import com.example.wheel_vet.model.Mascota
import com.example.wheel_vet.model.Usuario
import com.example.wheel_vet.model.UsuarioSesion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MascotaActivity : AppCompatActivity() {

    private lateinit var recyclerMascotas: RecyclerView
    private lateinit var adapter: MascotaAdapter
    private val listaMascotas = mutableListOf<Mascota>()

    private lateinit var apiService: ApiService

    private val idUsuario = 1 // Cambia esto por el id real

    private lateinit var btnAgregarMascota: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota)

        recyclerMascotas = findViewById(R.id.recyclerMascotas)
        recyclerMascotas.layoutManager = LinearLayoutManager(this)

        btnAgregarMascota = findViewById(R.id.btnAgregarMascota)
        btnAgregarMascota.setOnClickListener { mostrarDialogoAgregar() }

        adapter = MascotaAdapter(listaMascotas,
            onItemClick = { mascota ->
                Toast.makeText(this, "Clic en: ${mascota.nombremascota}", Toast.LENGTH_SHORT).show()
                mostrarDialogoCitasPorMascota(mascota.idmascota)
            },
            onDeleteClick = { mascota -> eliminarMascota(mascota) }
        )
        recyclerMascotas.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.18:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        cargarMascotas()
    }

    private fun cargarMascotas() {
        apiService.getMascotasPorUsuario(idUsuario).enqueue(object : Callback<List<Mascota>> {
            override fun onResponse(call: Call<List<Mascota>>, response: Response<List<Mascota>>) {
                if (response.isSuccessful) {
                    listaMascotas.clear()
                    response.body()?.let { listaMascotas.addAll(it) }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MascotaActivity, "Error al cargar mascotas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Mascota>>, t: Throwable) {
                Toast.makeText(this@MascotaActivity, "Fallo: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDialogoAgregar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Añadir nueva mascota")

        val viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_mascota, null, false)
        val inputNombre = viewInflated.findViewById<EditText>(R.id.inputNombre)
        val inputTipo = viewInflated.findViewById<EditText>(R.id.inputTipo)

        builder.setView(viewInflated)

        builder.setPositiveButton("Agregar") { dialog, _ ->
            val nombre = inputNombre.text.toString().trim()
            val tipo = inputTipo.text.toString().trim()
            if (nombre.isNotEmpty() && tipo.isNotEmpty()) {
                agregarMascota(nombre, tipo)
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun agregarMascota(nombre: String, tipo: String) {
        val usuario = Usuario(idusuario = idUsuario)
        val nuevaMascota = Mascota(
            nombremascota = nombre,
            tipomascota = tipo,
            usuario = usuario
        )

        apiService.crearMascota(nuevaMascota).enqueue(object : Callback<Mascota> {
            override fun onResponse(call: Call<Mascota>, response: Response<Mascota>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter.addMascota(it)
                        Toast.makeText(this@MascotaActivity, "Mascota agregada", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MascotaActivity, "Error al agregar mascota", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Mascota>, t: Throwable) {
                Toast.makeText(this@MascotaActivity, "Fallo: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun eliminarMascota(mascota: Mascota) {
        mascota.idmascota?.let { id ->
            apiService.eliminarMascota(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        adapter.removeMascota(mascota)
                        Toast.makeText(this@MascotaActivity, "Mascota eliminada", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MascotaActivity, "Error al eliminar mascota", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@MascotaActivity, "Fallo: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun mostrarDialogoCitasPorMascota(idmascota: Int?) {
        if (idmascota == null) {
            Toast.makeText(this, "Id de mascota no válido", Toast.LENGTH_SHORT).show()
            return
        }

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_citas_idmascota, null)
        val recyclerViewCitas = dialogView.findViewById<RecyclerView>(R.id.recyclerCitasMascota)
        recyclerViewCitas.layoutManager = LinearLayoutManager(this)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Citas de la mascota")
            .setView(dialogView)
            .setPositiveButton("Cerrar", null)
            .create()

        apiService.getCitasPorMascota(idmascota).enqueue(object : Callback<List<Cita>> {
            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                if (response.isSuccessful) {
                    val citas = response.body() ?: emptyList()
                    val adapter = CitasMascotaAdapter(citas)
                    recyclerViewCitas.adapter = adapter
                } else {
                    Toast.makeText(this@MascotaActivity, "No se pudieron cargar las citas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                Toast.makeText(this@MascotaActivity, "Error al cargar citas: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        dialog.show()
    }


}
