package com.example.wheel_vet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConductorActivity : AppCompatActivity() {

    private lateinit var btnPerfil: Button
    private lateinit var btnVerClinicas: Button
    private lateinit var btnVerCitas: Button
    private lateinit var btnVehiculo: Button
    private lateinit var btnListarVehiculo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conductor)

        btnPerfil = findViewById(R.id.btnPerfil)
        btnVerClinicas = findViewById(R.id.btnVerClinicas)
        btnVerCitas = findViewById(R.id.btnVerCitas)
        btnVehiculo = findViewById(R.id.btnVehiculo)
        btnListarVehiculo = findViewById(R.id.btnListarVehiculo)

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        btnVerClinicas.setOnClickListener {
            val intent = Intent(this, ListarClinicaActivity::class.java)
            startActivity(intent)
        }

        btnVerCitas.setOnClickListener {
            val intent = Intent(this, CitasAgenteActivity::class.java)
            startActivity(intent)
        }

        btnVehiculo.setOnClickListener {
            val intent = Intent(this, AnadirVehiculoActivity::class.java)
            startActivity(intent)
        }

        btnListarVehiculo.setOnClickListener {
            val intent = Intent(this, ListaVehiculosActivity::class.java)
            startActivity(intent)
        }
    }
}