package es.unex.giiis.fitlife365.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.data.Repository

class EvaluacionSaludViewModel (private val repository: Repository) : ViewModel() {
    suspend fun update(sexo: String, edad: Int, altura: Int, peso: Int, userId: Long?) {
       return repository.userDao.update(sexo, edad, altura, peso, userId)
    }

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast


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