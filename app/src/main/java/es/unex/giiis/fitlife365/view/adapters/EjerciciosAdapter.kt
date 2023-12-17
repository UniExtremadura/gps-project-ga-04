package es.unex.giiis.fitlife365.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.ExerciseModel

class EjerciciosAdapter : RecyclerView.Adapter<EjerciciosAdapter.EjercicioViewHolder>() {
    private var ejerciciosList: List<ExerciseModel> = mutableListOf()

    fun updateData(ejercicios: List<ExerciseModel>) {
        ejerciciosList = ejercicios
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ejercicio_rutina, parent, false)
        return EjercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val ejercicio = ejerciciosList[position]
        holder.bind(ejercicio)
    }

    override fun getItemCount(): Int {
        return ejerciciosList.size
    }

    public fun obtenerEjercicios(): List<ExerciseModel> {
        return ejerciciosList
    }

    inner class EjercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreEjercicio: TextView = itemView.findViewById(R.id.nombreEjercicio)
        private val tipoEjercicio: TextView = itemView.findViewById(R.id.tipoEjericio)
        private val musculoEjercicio: TextView = itemView.findViewById(R.id.musucloEjericio)
        private val dificultadEjercicio: TextView = itemView.findViewById(R.id.dificultadEjercicio)
        private val equipamientoEjercicio: TextView = itemView.findViewById(R.id.equipamientoEjercicio)
        private val instruccionesEjercicio: TextView = itemView.findViewById(R.id.instruccionesEjericio)

        private val checkboxEjercicio: CheckBox = itemView.findViewById(R.id.ejercicioCompletadoCheckBox)

        fun bind(ejercicio: ExerciseModel) {
            nombreEjercicio.text = ejercicio.name
            tipoEjercicio.text = ejercicio.type
            musculoEjercicio.text = ejercicio.muscle
            dificultadEjercicio.text = ejercicio.difficulty
            equipamientoEjercicio.text = ejercicio.equipment
            instruccionesEjercicio.text = ejercicio.instructions

            checkboxEjercicio.isChecked = ejercicio.isCompleted
            checkboxEjercicio.setOnCheckedChangeListener { _, isChecked ->
                ejercicio.isCompleted = isChecked
            }
        }
    }
}
