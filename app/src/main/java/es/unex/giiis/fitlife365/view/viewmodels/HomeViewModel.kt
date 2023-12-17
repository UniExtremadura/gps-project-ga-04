package es.unex.giiis.fitlife365.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User

class HomeViewModel : ViewModel() {
    private val _user = MutableLiveData<User>(null)
    val user: LiveData<User>
        get() = _user

    var userInSession: User? = null
        set(value) {
            field = value
            _user.value = value!!
        }

    private val _navigateToRoutines = MutableLiveData<Routine>(null)
    val navigateToRoutines: LiveData<Routine>
        get() = _navigateToRoutines

    fun onShowClick(rutina: Routine) {
        _navigateToRoutines.value = rutina
    }



}