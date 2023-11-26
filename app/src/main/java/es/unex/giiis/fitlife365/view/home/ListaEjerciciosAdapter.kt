package es.unex.giiis.fitlife365.view.home


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.fitlife365.databinding.ItemExerciseBinding
import es.unex.giiis.fitlife365.model.ExerciseModel

class ListaEjerciciosAdapter(
    private var exercises: List<ExerciseModel>,
) : RecyclerView.Adapter<ListaEjerciciosAdapter.ShowViewHolder>() {


    class ShowViewHolder(
        private val binding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: ExerciseModel, totalItems: Int) {
            with(binding) {
                tvName.text = exercise.name
                tvType.text = exercise.type
                tvMuscle.text = exercise.muscle
                tvEquipment.text = exercise.equipment
                tvDifficulty.text = exercise.difficulty
                tvInstructions.text = exercise.instructions
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShowViewHolder(binding)
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