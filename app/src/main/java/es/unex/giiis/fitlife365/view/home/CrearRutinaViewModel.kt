package es.unex.giiis.fitlife365.view.home

import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import kotlinx.coroutines.launch

class CrearRutinaViewModel (private val repository: Repository
): ViewModel(){
    private lateinit var nombreRutina : EditText
    private lateinit var pesoObjetivoRutina : EditText
    private lateinit var currentUser : User
    fun setUser( user : User){
        currentUser = user
    }
     fun crearRutina(view: View, diasEntrenamiento : String) : Routine {

         nombreRutina = view.findViewById(R.id.editTextText)
         pesoObjetivoRutina = view.findViewById(R.id.editTextNumber)

        val nombre = "Por defecto"
        val nombreRutina = nombreRutina.text.toString().takeIf { it.isNotEmpty() } ?: nombre

        val peso = 60
        val pesoObjetivoRutina = pesoObjetivoRutina.text.toString().toIntOrNull() ?: peso

        val routine = Routine(
            routineId = null,
            userId = currentUser.userId,  // Asegúrate de tener currentUser definido en tu fragmento o ViewModel
            name = nombreRutina,
            pesoObjetivo = pesoObjetivoRutina,
            diasEntrenamiento = diasEntrenamiento,
            ejercicios = ""
        )

        viewModelScope.launch {
            val id = repository.insertRoutine(routine)
            routine.routineId = id
        }
         return routine
    }

    companion object { val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            return CrearRutinaViewModel(
                (application as FitLife365Application).appContainer.repository, ) as T }
    }
    }
}