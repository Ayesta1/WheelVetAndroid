package com.example.wheel_vet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class GeneralActivity : AppCompatActivity() {

    private lateinit var btnPerfil: Button
    private lateinit var btnClinicas: Button
    private lateinit var btnCitas: Button
    private lateinit var btnChat: Button
    private lateinit var btnMascotas: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)

        btnPerfil = findViewById(R.id.btnPerfil)
        btnClinicas = findViewById(R.id.btnVerClinicas)
        btnCitas = findViewById(R.id.btnVerCitas)
        btnMascotas = findViewById(R.id.btnMascotas)

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        btnClinicas.setOnClickListener {
            val intent = Intent(this, ListarClinicaActivity::class.java)
            startActivity(intent)
        }

        btnCitas.setOnClickListener {
            startActivity(Intent(this, CitasActivity::class.java))
        }

        btnMascotas.setOnClickListener {
            val intent = Intent(this, MascotaActivity::class.java)
            startActivity(intent)
        }
    }

}
