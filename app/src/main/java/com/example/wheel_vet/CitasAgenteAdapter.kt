package com.example.wheel_vet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Cita

class CitasAgenteAdapter(
    private val citas: List<Cita>,
) : RecyclerView.Adapter<CitasAgenteAdapter.CitaViewHolder>() {

    inner class CitaViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val descripcion: TextView = view.findViewById(R.id.textDescripcion)
        val fechaHora: TextView = view.findViewById(R.id.textFechaHora)
        val nombreMascota: TextView = view.findViewById(R.id.textNombreMascota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita_agente, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.descripcion.text = cita.descripcion ?: "Sin descripción"
        holder.nombreMascota.text = cita.mascota?.nombremascota ?: "Sin mascota"
        holder.fechaHora.text = "Fecha: ${cita.dia ?: "N/A"} Hora: ${cita.hora ?: "N/A"}"
    }


    override fun getItemCount() = citas.size
}
