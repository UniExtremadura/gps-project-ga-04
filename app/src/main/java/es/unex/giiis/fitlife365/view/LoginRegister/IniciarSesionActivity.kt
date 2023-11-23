package es.unex.giiis.fitlife365.view.LoginRegister

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.room.Room
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.databinding.ActivityLoginBinding
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.utils.CredentialCheck
import es.unex.giiis.fitlife365.view.home.HomeActivity
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

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            applyFont(window.decorView, selectedFont)
        }

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

    private fun applyFont(view: View, fontName: String) {
        when (view) {
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    applyFont(view.getChildAt(i), fontName)
                }
            }
            is TextView -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is EditText -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente a la barra de edición de texto
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is Button -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente al botón
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
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
                    else navigateToHomeActivity(user!!)
                }
                else
                    showToast("Invalid username")
            }
        }
        else
            showToast(check.msg)
    }

    private fun navigateToHomeActivity(user: User) {
        HomeActivity.start(this, user)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}
