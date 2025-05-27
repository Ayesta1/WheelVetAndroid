package com.example.wheel_vet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Cita

class CitaAdapter(private val citas: List<Cita>) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    inner class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDescripcion: TextView = itemView.findViewById(R.id.textDescripcion)
        val nombreMascota: TextView = itemView.findViewById(R.id.textNombreMascota)
        val textFecha: TextView = itemView.findViewById(R.id.textFecha)
        val textHora: TextView = itemView.findViewById(R.id.textHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.textDescripcion.text = cita.descripcion
        holder.nombreMascota.text = cita.mascota?.nombremascota ?: "Sin nombre"
        holder.textFecha.text = cita.dia
        holder.textHora.text = cita.hora
    }

    override fun getItemCount(): Int = citas.size
}
