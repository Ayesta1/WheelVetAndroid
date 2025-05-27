package com.example.wheel_vet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AgenteActivity : AppCompatActivity() {

    private lateinit var btnPerfil: Button
    private lateinit var btnVerClinicas: Button
    private lateinit var btnVerCitas: Button
    private lateinit var btnMascotasTratadas: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agente)

        btnPerfil = findViewById(R.id.btnPerfil)
        btnVerClinicas = findViewById(R.id.btnVerClinicas)
        btnVerCitas = findViewById(R.id.btnVerCitas)
        btnMascotasTratadas = findViewById(R.id.btnMascotasTratadas)

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

        btnMascotasTratadas.setOnClickListener {
            val intent = Intent(this, MascotaTratadaActivity::class.java)
            startActivity(intent)
        }
    }
}
