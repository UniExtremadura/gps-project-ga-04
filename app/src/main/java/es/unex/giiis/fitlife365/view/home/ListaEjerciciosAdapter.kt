package es.unex.giiis.fitlife365.view.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.fitlife365.databinding.ItemExerciseBinding
import es.unex.giiis.fitlife365.model.ExerciseModel

class ListaEjerciciosAdapter(
    private var exercises: List<ExerciseModel>,
    private val onItemClick: (ExerciseModel) -> Unit
) : RecyclerView.Adapter<ListaEjerciciosAdapter.ShowViewHolder>() {


    class ShowViewHolder(
        private val binding: ItemExerciseBinding,
        private val onItemClick: (ExerciseModel) -> Unit // Agrega esta línea
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: ExerciseModel, size: Int) {
            with(binding) {
                tvName.text = exercise.name
                tvType.text = exercise.type
                checkBoxNombre.isChecked = exercise.isSelected

                itemView.setOnClickListener {
                    onItemClick(exercise)
                }

                checkBoxNombre.setOnCheckedChangeListener { _, isChecked ->
                    exercise.isSelected = isChecked
                }
            }
        }
    }

    fun getSelectedExercises(): List<ExerciseModel> {
        return exercises.filter { it.isSelected }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShowViewHolder(binding, onItemClick) // Pasa onItemClick aquí
    }

    override fun getItemCount() = exercises.size

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(exercises[position], exercises.size)
    }


    fun updateData(exercises: List<ExerciseModel>) {
        this.exercises = exercises
        Log.d("ListaEjerciciosAdapter", "updateData llamado con ${exercises.size} elementos")
        notifyDataSetChanged()
    }

}