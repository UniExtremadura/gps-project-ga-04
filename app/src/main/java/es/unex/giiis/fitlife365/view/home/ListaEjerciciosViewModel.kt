package es.unex.giiis.fitlife365.view.home

import android.text.Spannable.Factory
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.api.APIError
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListaEjerciciosViewModel (
    private val repository:  Repository

): ViewModel(){
    var user: User? = null

    var routine: Routine? = null
    var dificultad: String? = null

    val exercises = repository.exercices

    var musculo: String? = null


    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _toast = MutableLiveData<String?>()

    val toast: LiveData<String?>
        get() = _toast


    init {
        refresh()
    }
    fun refresh() {
        launchDataLoad { repository.tryUpdateRecentExercicesCache("biceps", "beginner") }
    }

    fun onToastShown() {
        _toast.value = null
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: APIError) {
                _toast.value = error.message
            } finally {
                _spinner.value = true
            }
        }
    }

    suspend fun getRoutinebyID(routineId: Long?): Routine {
        return repository.getRoutinebyID(routineId)
    }

    suspend fun insert(ejercicio: ExerciseModel): Long? {
        return repository.insert(ejercicio)
    }

    fun addRoutineExercise(id: Long?, routineId: Long?) {
        viewModelScope.launch {
            repository.addRoutineExercise(id, routineId)
        }
    }

    fun updateRoutine(routineId: Long?, cadenaEjerciciosNueva: String) {
        viewModelScope.launch {
            repository.updateRoutine(routineId, cadenaEjerciciosNueva)
        }
    }

    companion object { val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            return ListaEjerciciosViewModel(
                (application as FitLife365Application).appContainer.repository, ) as T }
        }
    }
}