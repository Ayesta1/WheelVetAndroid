package com.example.wheel_vet

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.*
import com.example.wheel_vet.model.UsuarioSesion.usuario
import com.example.wheel_vet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitasActivity : AppCompatActivity() {

    private val apiService = RetrofitClient.instance
    private val conductores = mutableListOf<Conductor>()
    private val mascotas = mutableListOf<Mascota>()
    private val agentes = mutableListOf<AgenteVeterinario>()
    private val clinicas = mutableListOf<ClinicaVeterinaria>()
    private lateinit var recyclerCitas: RecyclerView

    private val citas = mutableListOf<Cita>()
    private lateinit var citaAdapter: CitaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        recyclerCitas = findViewById(R.id.recyclerCitas)
        recyclerCitas.layoutManager = LinearLayoutManager(this)

        citaAdapter = CitaAdapter(citas)
        recyclerCitas.adapter = citaAdapter

        // Cargar citas del usuario al iniciar la pantalla
        cargarCitasDelUsuario()

        findViewById<Button>(R.id.btnCrearCita).setOnClickListener {
            mostrarDialogoCrear()
        }
    }

    private fun cargarCitasDelUsuario() {
        val usuarioId = UsuarioSesion.usuario?.idusuario ?: return
        apiService.getCitasPorUsuario(usuarioId).enqueue(object : Callback<List<Cita>> {
            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                if (response.isSuccessful) {
                    citas.clear()
                    response.body()?.let { citas.addAll(it) }
                    citaAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@CitasActivity, "Error al cargar citas", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                Toast.makeText(this@CitasActivity, "Fallo en la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDialogoCrear() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_crear_cita, null)

        val spinnerConductor = view.findViewById<Spinner>(R.id.spinnerConductor)
        val spinnerMascota = view.findViewById<Spinner>(R.id.spinnerMascota)
        val spinnerAgente = view.findViewById<Spinner>(R.id.spinnerAgente)
        val spinnerClinica = view.findViewById<Spinner>(R.id.spinnerClinica)

        val inputDescripcion = view.findViewById<EditText>(R.id.inputDescripcion)
        val inputFecha = view.findViewById<EditText>(R.id.inputFecha)
        val inputHora = view.findViewById<EditText>(R.id.inputHora)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Crear nueva cita")
            .setView(view)
            .setPositiveButton("Crear", null)
            .setNegativeButton("Cancelar") { d, _ -> d.dismiss() }
            .create()

        cargarDatosParaSpinners(spinnerConductor, spinnerMascota, spinnerAgente, spinnerClinica) {
            dialog.show()

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val descripcion = inputDescripcion.text.toString().trim()
                val fecha = inputFecha.text.toString().trim()
                val hora = inputHora.text.toString().trim()

                if (descripcion.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val agenteUsuario = agentes[spinnerAgente.selectedItemPosition].usuario
                if (agenteUsuario?.idusuario == null) {
                    Toast.makeText(this, "Agente inválido", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val nuevaCita = Cita(
                    idcita = null,
                    usuario = usuario,
                    mascota = mascotas[spinnerMascota.selectedItemPosition],
                    clinica = clinicas[spinnerClinica.selectedItemPosition],
                    conductor = conductores[spinnerConductor.selectedItemPosition].usuario,
                    agente = Usuario(idusuario = agenteUsuario.idusuario),
                    descripcion = descripcion,
                    dia = fecha,
                    hora = hora,
                )


                apiService.crearCita(nuevaCita).enqueue(object : Callback<Cita> {
                    override fun onResponse(call: Call<Cita>, response: Response<Cita>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@CitasActivity, "Cita creada", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(this@CitasActivity, "Error al crear la cita", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Cita>, t: Throwable) {
                        Toast.makeText(this@CitasActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun cargarDatosParaSpinners(
        spinnerConductor: Spinner,
        spinnerMascota: Spinner,
        spinnerAgente: Spinner,
        spinnerClinica: Spinner,
        onComplete: () -> Unit
    ) {
        val usuarioId = UsuarioSesion.usuario?.idusuario ?: return
        val ciudadId = UsuarioSesion.usuario?.ciudad?.idciudad ?: return

        var loaded = 0
        fun checkLoaded() { if (++loaded == 4) onComplete() }

        apiService.getConductores().enqueue(object : Callback<List<Conductor>> {
            override fun onResponse(call: Call<List<Conductor>>, response: Response<List<Conductor>>) {
                conductores.clear()
                response.body()?.let {
                    conductores.addAll(it)
                    spinnerConductor.adapter = ArrayAdapter(
                        this@CitasActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        it.map { c -> c.usuario?.nombreusuario ?: "Sin nombre" }
                    )
                }
                checkLoaded()
            }
            override fun onFailure(call: Call<List<Conductor>>, t: Throwable) = checkLoaded()
        })

        apiService.getMascotasPorUsuario(usuarioId).enqueue(object : Callback<List<Mascota>> {
            override fun onResponse(call: Call<List<Mascota>>, response: Response<List<Mascota>>) {
                mascotas.clear()
                response.body()?.let {
                    mascotas.addAll(it)
                    spinnerMascota.adapter = ArrayAdapter(
                        this@CitasActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        it.map { m -> m.nombremascota ?: "Sin nombre" }
                    )
                }
                checkLoaded()
            }
            override fun onFailure(call: Call<List<Mascota>>, t: Throwable) = checkLoaded()
        })

        apiService.getAgentesVeterinarios().enqueue(object : Callback<List<AgenteVeterinario>> {
            override fun onResponse(call: Call<List<AgenteVeterinario>>, response: Response<List<AgenteVeterinario>>) {
                agentes.clear()
                response.body()?.let {
                    agentes.addAll(it)
                    spinnerAgente.adapter = ArrayAdapter(
                        this@CitasActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        it.map { a -> a.usuario?.nombreusuario ?: "Sin nombre" }
                    )
                }
                checkLoaded()
            }
            override fun onFailure(call: Call<List<AgenteVeterinario>>, t: Throwable) = checkLoaded()
        })

        apiService.getClinicasPorCiudad(ciudadId).enqueue(object : Callback<List<ClinicaVeterinaria>> {
            override fun onResponse(call: Call<List<ClinicaVeterinaria>>, response: Response<List<ClinicaVeterinaria>>) {
                clinicas.clear()
                response.body()?.let {
                    clinicas.addAll(it)
                    spinnerClinica.adapter = ArrayAdapter(
                        this@CitasActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        it.map { c -> c.nombreclinica }
                    )
                }
                checkLoaded()
            }
            override fun onFailure(call: Call<List<ClinicaVeterinaria>>, t: Throwable) = checkLoaded()
        })
    }
}
