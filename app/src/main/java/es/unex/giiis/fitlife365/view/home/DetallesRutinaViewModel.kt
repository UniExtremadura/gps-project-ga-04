import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.home.EjerciciosAdapter
import es.unex.giiis.fitlife365.view.home.ListaEjerciciosViewModel
import kotlinx.coroutines.launch

class DetallesRutinaViewModel(private val repository: Repository) : ViewModel() {

    //private lateinit var ejerciciosAdapter: EjerciciosAdapter
    //private lateinit var rutina : Routine
    //lateinit var user : User


    fun eliminarRutina(rutina: Routine){
        viewModelScope.launch {
            repository.deleteRoutine(rutina.routineId)
        }
    }

     fun updateCompletionStatusForAllExercises(ejerciciosAdapter: EjerciciosAdapter) {
        viewModelScope.launch { val updatedExerciseList = ejerciciosAdapter.obtenerEjercicios().map {
            repository.updateExercise(it)
            }
        }
    }

    fun mostrarDetallesDeTodosLosEjercicios(listaEjerciciosIds: String?, adapter: EjerciciosAdapter) {
        if (listaEjerciciosIds.isNullOrBlank()) {
            return
        }

        val listaIds = listaEjerciciosIds.split(",").map { it.trim() }

        viewModelScope.launch {
            val ejerciciosList = mutableListOf<ExerciseModel>()

            for (exerciseId in listaIds) {
                val exercise = repository.getExerciseById(exerciseId.toLong())

                exercise?.let { ejerciciosList.add(it) }
            }

            adapter.updateData(ejerciciosList)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return DetallesRutinaViewModel(
                    (application as FitLife365Application).appContainer.repository,
                ) as T
            }
        }
    }
}
