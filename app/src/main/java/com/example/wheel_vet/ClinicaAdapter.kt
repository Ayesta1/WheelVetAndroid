package com.example.wheel_vet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.ClinicaVeterinaria

class ClinicaAdapter(private val clinicas: List<ClinicaVeterinaria>) :
    RecyclerView.Adapter<ClinicaAdapter.ClinicaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_clinica, parent, false)
        return ClinicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClinicaViewHolder, position: Int) {
        val clinica = clinicas[position]
        holder.nombre.text = clinica.nombreclinica
        holder.direccion.text = clinica.direccion ?: "Sin dirección"
        holder.telefono.text = clinica.telefonoclinica ?: "Sin teléfono"
    }

    override fun getItemCount(): Int = clinicas.size

    class ClinicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombreClinica)
        val direccion: TextView = itemView.findViewById(R.id.tvDireccionClinica)
        val telefono: TextView = itemView.findViewById(R.id.tvTelefonoClinica)
    }
}

