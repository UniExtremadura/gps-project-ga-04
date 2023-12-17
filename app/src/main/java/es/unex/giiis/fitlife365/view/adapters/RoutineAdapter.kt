package es.unex.giiis.fitlife365.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.Routine

class RoutineAdapter(
    private var rutinas: List<Routine>,
    private val onItemClickListener: (Routine) -> Unit
) : RecyclerView.Adapter<RoutineAdapter.RutinaViewHolder>() {

    class RutinaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreRutina: Button = itemView.findViewById(R.id.btnRutina)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_routine, parent, false)
        return RutinaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int) {
        val rutina = rutinas[position]
        holder.nombreRutina.text = rutina.name

        holder.nombreRutina.setOnClickListener {
            onItemClickListener(rutina)
        }
    }

    fun actualizarListaRutinas(nuevaLista: List<Routine>) {
        rutinas = nuevaLista
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return rutinas.size
    }
}

