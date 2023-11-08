package es.unex.giiis.fitlife365.view.LoginRegister

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.databinding.ActivityLoginBinding
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.home.MisRutinasActivity

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var btnIniciarSesion: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var textViewRegister: TextView
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnIniciarSesion = binding.buttonContinuar
        etUsername = binding.etUsername
        etPassword = binding.etPassword
        textViewRegister = binding.textView3

        setUpListeners()
    }

    private fun validateFields(): Boolean {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        // Verificar si todos los campos tienen contenido
        val allFieldsNotEmpty = username.isNotEmpty() && password.isNotEmpty()
        if (!allFieldsNotEmpty) {
            return false
        }

        // Verificar cualquier otra validación necesaria

        return true
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun setUpListeners() {
        with (binding) {
            btnIniciarSesion.setOnClickListener {
                if (!validateFields()) {
                    showToast("Por favor, completa todos los campos correctamente.")
                    return@setOnClickListener
                }
                navigateToMisRutinas(User(etUsername.text.toString(), etPassword.text.toString()))
            }
        }
    }

    private fun navigateToMisRutinas(user: User) {
        MisRutinasActivity.start(this, user)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}
