package com.example.wheel_vet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Mascota

class MascotaAdapter(
    private val mascotas: MutableList<Mascota>,
    private val onItemClick: (Mascota) -> Unit,
    private val onDeleteClick: (Mascota) -> Unit
) : RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder>() {

    inner class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNombre: TextView = itemView.findViewById(R.id.textNombreMascota)
        val textTipoMascota: TextView = itemView.findViewById(R.id.textTipoMascota)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarMascota)

        fun bind(mascota: Mascota) {
            textNombre.text = mascota.nombremascota
            textTipoMascota.text = mascota.tipomascota

            itemView.setOnClickListener { onItemClick(mascota) }
            btnEliminar.setOnClickListener { onDeleteClick(mascota) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        holder.bind(mascotas[position])
    }

    override fun getItemCount(): Int = mascotas.size

    fun addMascota(mascota: Mascota) {
        mascotas.add(mascota)
        notifyItemInserted(mascotas.size - 1)
    }

    fun removeMascota(mascota: Mascota) {
        val pos = mascotas.indexOf(mascota)
        if (pos != -1) {
            mascotas.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }
}

