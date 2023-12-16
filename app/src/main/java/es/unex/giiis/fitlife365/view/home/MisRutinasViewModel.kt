package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import kotlinx.coroutines.launch

class MisRutinasViewModel (private val repository: Repository) : ViewModel() {
    suspend fun getRoutinesByUser(userId: Long?): List<Routine>? {
        return repository.routineDao.getRoutinesByUser(userId)
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
            return MisRutinasViewModel(
                (application as FitLife365Application).appContainer.repository, ) as T }
        }
    }



}
