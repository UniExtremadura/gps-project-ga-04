package es.unex.giiis.fitlife365.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.model.User
import kotlinx.coroutines.launch

class EvaluacionSaludViewModel (private val repository: Repository) : ViewModel() {

    var  user : User? = null
    var edad: Int = 0
    var estatura: Int = 0
    var peso: Int = 0
    var sexo: String = ""
    var userID: Long = 0

    suspend fun update() {
        return repository.update(sexo, edad, estatura, peso, userID)
    }

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun guardarInformacionUsuario() {
        viewModelScope.launch {
            repository.update(sexo, edad, estatura, peso, userID)
        }
    }



    companion object { val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            return EvaluacionSaludViewModel(
                (application as FitLife365Application).appContainer.repository, ) as T }
    }
    }
}