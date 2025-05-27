package com.example.wheel_vet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Cita

class MascotaTratadaAdapter(
    private val citas: List<Cita>
) : RecyclerView.Adapter<MascotaTratadaAdapter.MascotaTratadaViewHolder>() {

    inner class MascotaTratadaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreMascota: TextView = view.findViewById(R.id.textNombreMascota)
        val nombreAgente: TextView = view.findViewById(R.id.textNombreAgente)
        val fechaHora: TextView = view.findViewById(R.id.textFechaHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaTratadaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mascota_tratada, parent, false)
        return MascotaTratadaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MascotaTratadaViewHolder, position: Int) {
        val cita = citas[position]
        holder.nombreMascota.text = cita.mascota?.nombremascota ?: "Sin nombre"
        holder.nombreAgente.text = cita.agente?.nombreusuario ?: "Sin agente"
        holder.fechaHora.text = "Fecha: ${cita.dia ?: "N/A"} Hora: ${cita.hora ?: "N/A"}"
    }

    override fun getItemCount(): Int = citas.size
}







