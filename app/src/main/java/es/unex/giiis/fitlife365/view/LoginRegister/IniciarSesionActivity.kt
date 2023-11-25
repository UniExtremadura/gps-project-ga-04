package es.unex.giiis.fitlife365.view.LoginRegister

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.databinding.ActivityLoginBinding
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.utils.CredentialCheck
import kotlinx.coroutines.launch

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var db: FitLife365Database

    private lateinit var btnIniciarSesion: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var textViewRegister: TextView
    private lateinit var binding: ActivityLoginBinding
    private lateinit var btnRegistro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        db = FitLife365Database.getInstance(applicationContext)!!

        /*applicationContext.deleteDatabase("fitlife365.db")

        if(db.isOpen)
            db.close()

        db = Room.databaseBuilder(applicationContext, FitLife365Database::class.java, "fitlife365.db").build()
        */



        btnIniciarSesion = binding.buttonContinuar
        btnRegistro = binding.registrateButton2
        etUsername = binding.etUsername
        etPassword = binding.etPassword
        textViewRegister = binding.textView3

        setUpListeners()
        readSettings()
    }



    private fun readSettings() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val rememberme = preferences.getBoolean("rememberme", false)
        val username = preferences.getString("username", "") ?: ""
        val password = preferences.getString("password", "") ?: ""

        if (rememberme) {
            binding.etUsername.setText(username)
            binding.etPassword.setText(password)
        }
    }


    private fun validateFields(): Boolean {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        // Verificar si todos los campos tienen contenido
        val allFieldsNotEmpty = username.isNotEmpty() && password.isNotEmpty()
        if (!allFieldsNotEmpty) {
            showToast("Por favor, completa todos los campos correctamente.")
            return false
        }

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
                    return@setOnClickListener
                }
                checkLogin()
            }
            btnRegistro.setOnClickListener {
                navigateToRegister()
            }
        }
    }

    private fun checkLogin(){
        val check = CredentialCheck.login(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        if (!check.fail){
            lifecycleScope.launch{
                val user = db?.userDao()?.findByName(binding.etUsername.text.toString())
                if (user != null) {
                    val check = CredentialCheck.passwordOk(binding.etPassword.text.toString(), user.password)
                    if (check.fail)
                        showToast(check.msg)
                    else navigateToRegister()
                }
                else
                    showToast("Invalid username")
            }
        }
        else
            showToast(check.msg)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}
