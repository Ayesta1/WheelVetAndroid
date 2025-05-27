package com.example.wheel_vet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Cita

class CitasMascotaAdapter(private val citas: List<Cita>) : RecyclerView.Adapter<CitasMascotaAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvDia: TextView = itemView.findViewById(R.id.tvDia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita_mascota, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = citas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cita = citas[position]
        holder.tvDescripcion.text = cita.descripcion
        holder.tvDia.text = cita.dia
    }
}
