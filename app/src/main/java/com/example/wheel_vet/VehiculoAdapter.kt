package com.example.wheel_vet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheel_vet.model.Vehiculo

class VehiculoAdapter(
    private val lista: List<Vehiculo>,
    private val onEditar: (Vehiculo) -> Unit,
    private val onEliminar: (Vehiculo) -> Unit
) : RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>() {

    inner class VehiculoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textMatricula: TextView = view.findViewById(R.id.textMatricula)
        val textMarcaModelo: TextView = view.findViewById(R.id.textMarcaModelo)
        val textColor: TextView = view.findViewById(R.id.textColor)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vehiculo, parent, false)
        return VehiculoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehiculoViewHolder, position: Int) {
        val vehiculo = lista[position]
        holder.textMatricula.text = vehiculo.matricula
        holder.textMarcaModelo.text = "${vehiculo.marca} ${vehiculo.modelo}"
        holder.textColor.text = vehiculo.color

        holder.btnEditar.setOnClickListener { onEditar(vehiculo) }
        holder.btnEliminar.setOnClickListener { onEliminar(vehiculo) }
    }

    override fun getItemCount(): Int = lista.size
}

