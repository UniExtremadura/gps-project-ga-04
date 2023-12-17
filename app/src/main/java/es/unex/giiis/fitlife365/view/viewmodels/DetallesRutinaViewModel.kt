package es.unex.giiis.fitlife365.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.view.adapters.EjerciciosAdapter
import kotlinx.coroutines.launch

class DetallesRutinaViewModel(private val repository: Repository) : ViewModel() {

    private var ejerciciosAdapter: EjerciciosAdapter = EjerciciosAdapter()
    lateinit var rutina : Routine
    lateinit var adapter: EjerciciosAdapter
    private lateinit var listaEjerciciosIds: String



    fun eliminarRutina(){
        viewModelScope.launch {
            repository.deleteRoutine(rutina.routineId)
        }
    }

     fun updateCompletionStatusForAllExercises() {
        viewModelScope.launch { val updatedExerciseList = ejerciciosAdapter.obtenerEjercicios().map {
            repository.updateExercise(it)
            }
        }
    }

    fun mostrarDetallesDeTodosLosEjercicios() {
        listaEjerciciosIds = rutina.ejercicios
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
