import android.content.Intent
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.MainActivity
import es.unex.giiis.fitlife365.view.home.EvaluacionSaludActivity
import es.unex.giiis.fitlife365.view.home.ListaEjerciciosViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditarPerfilViewModel(private val repository: Repository) : ViewModel() {

    lateinit var user : User

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage

    fun updateUser() {
        viewModelScope.launch {
            try {
                // Lógica para actualizar el usuario en el repositorio
                repository.update(user.sexo, user.edad, user.altura, user.peso, user.userId)

                // Emite un mensaje Toast después de la actualización
                _toastMessage.postValue("Perfil actualizado exitosamente")
            } catch (e: Exception) {
                // Manejo de errores, puedes personalizarlo según tus necesidades
                _toastMessage.postValue("Error al actualizar el perfil")
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            try {
                // Lógica para eliminar el usuario en el repositorio
                repository.deleteUser(user)

                // Emite un mensaje Toast después de la eliminación
                _toastMessage.postValue("Perfil eliminado exitosamente")
            } catch (e: Exception) {
                // Manejo de errores, puedes personalizarlo según tus necesidades
                _toastMessage.postValue("Error al eliminar el perfil")
            }
        }
    }

     fun eliminarUsuario() {
        // Llamar al método deleteUser del UserDao
         viewModelScope.launch(Dispatchers.IO) {
                deleteUser()
        }
    }

     fun actualizarUsuario() {

        // Llamar al método updateUser del UserDao
        viewModelScope.launch(Dispatchers.IO) {
            updateUser()
                }
            }

    //haz el companion object
    companion object { val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            return EditarPerfilViewModel(
                (application as FitLife365Application).appContainer.repository, ) as T }
    }
    }

}
