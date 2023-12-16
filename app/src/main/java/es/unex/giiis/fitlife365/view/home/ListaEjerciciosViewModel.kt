package es.unex.giiis.fitlife365.view.home

import android.text.Spannable.Factory
import android.util.Log
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
import es.unex.giiis.fitlife365.api.getNetworkService
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.data.toExercise
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListaEjerciciosViewModel (
    private val repository:  Repository

): ViewModel(){
    var user: User? = null

    var rutina: Routine? = null
    var dificultad: String = "beginner"
    val exercises = repository.exercices
    var musculo: String = "biceps"

    private var _exercise: List<ExerciseModel> = emptyList()
    lateinit var adapter: ListaEjerciciosAdapter


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
        launchDataLoad { repository.tryUpdateRecentExercicesCache(musculo, dificultad) }
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

    private val muscleMapping = mapOf(
        "Abdominales" to "abdominals",
        "Abductores"  to "abductors",
        "Aductores" to "adductors",
        "Bíceps" to "biceps",
        "Pantorrillas" to "calves",
        "Pecho" to "chest",
        "Antebrazos" to "forearms",
        "Glúteos" to "glutes",
        "Isquiotibiales" to "hamstrings",
        "Dorsales" to "lats",
        "Lumbares" to "lower_back",
        "Espalda" to "middle_back",
        "Cuello" to "neck",
        "Cuadríceps" to "quadriceps",
        "Trapecios" to "traps",
        "Tríceps" to "triceps"
    )

    private val difficultyMapping = mapOf(
        "Principiante" to "beginner",
        "Intermedio" to "intermediate",
        "Avanzado" to "expert"
    )

    fun filtrarEjerciciosPorMusculo(muscle: String, difficulty: String) {
        viewModelScope.launch {
            try {
                // Traducir el nombre del músculo al inglés utilizando el mapeo
                val muscleInEnglish = muscleMapping[muscle] ?: muscle

                // Traducir la dificultad al inglés utilizando el mapeo
                val difficultyInEnglish = difficultyMapping[difficulty] ?: difficulty

                // Filtrar ejercicios por músculo y dificultad (usando el nombre en inglés)
                _exercise = repository.getExercisesByMuscleAndDifficulty(muscleInEnglish, difficultyInEnglish).toExercise()
                adapter.updateData(_exercise)

            } catch (error: APIError) {
                _toast.value = error.message
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

    fun insertarEjerRutina(){
        val listaEjerciciosSeleccionados = adapter.getSelectedExercises()
        viewModelScope.launch {
            // Obtener la rutina actual
            //val routineEntity = rutinaDao?.getRoutineById(rutina.routineId)
            val routineEntity = getRoutinebyID(rutina!!.routineId)

            // Obtener la cadena de ejercicios anterior
            val cadenaEjerciciosAnterior = routineEntity?.ejercicios ?: ""

            // Insertar cada ejercicio seleccionado en ExerciseModelDao
            for (ejercicio in listaEjerciciosSeleccionados) {
                //val id = exerciseModelDao?.insert(ejercicio)
                val id = insert(ejercicio)
                ejercicio.exerciseId = id
                Log.d("Ejercicio insertado: ", ejercicio.exerciseId.toString())
                addRoutineExercise(id, rutina!!.routineId)
                Log.d("Ejercicio añadido a rutina: ", rutina!!.routineId.toString())
            }

            // Concatenar los ejercicios anteriores con los nuevos
            val cadenaEjerciciosNueva = if (cadenaEjerciciosAnterior.isNotEmpty()) {
                cadenaEjerciciosAnterior + "," + listaEjerciciosSeleccionados.joinToString(",") { it.exerciseId.toString() }
            } else {
                listaEjerciciosSeleccionados.joinToString(",") { it.exerciseId.toString() }
            }

            // Actualizar la cadena de ejercicios en la rutina
            updateRoutine(rutina!!.routineId, cadenaEjerciciosNueva)
            Log.d("Ejercicios añadidos a rutina: ", cadenaEjerciciosNueva)
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