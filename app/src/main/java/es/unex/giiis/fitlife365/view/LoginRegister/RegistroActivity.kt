package es.unex.giiis.fitlife365.view.LoginRegister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.databinding.ActivityRegisterBinding
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.home.EvaluacionSalud
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {

    private lateinit var db: FitLife365Database

    private lateinit var btnContinuar: Button
    private lateinit var registerUsername: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerEmail: EditText
    private lateinit var registerConfirmPassword: EditText
    private lateinit var checkBoxPassword: CheckBox
    private lateinit var binding: ActivityRegisterBinding




    companion object {
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
        const val EMAIL = "EMAIL"

        fun start(context: Context, responseLuncher: ActivityResultLauncher<Intent>) {
            val intent = Intent(context, RegistroActivity::class.java)
            responseLuncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            applyFont(window.decorView, selectedFont)
        }

        db = FitLife365Database.getInstance(applicationContext)!!

        btnContinuar = findViewById(R.id.idContinuarRegistro)
        registerUsername = findViewById(R.id.registerUsername)
        registerPassword = findViewById(R.id.registerPassword)
        registerEmail = findViewById(R.id.registerEmail)
        registerConfirmPassword = findViewById(R.id.registerConfirmPassword)
        checkBoxPassword = findViewById(R.id.checkBoxPassword)



        setUpListeners()
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

    private fun validateFields() : Boolean {
        val username = registerUsername.text.toString()
        val password = registerPassword.text.toString()
        val email = registerEmail.text.toString()
        val confirmPassword = registerConfirmPassword.text.toString()

        // Verificar si todos los campos tienen contenido
        val allFieldsNotEmpty = username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && confirmPassword.isNotEmpty()
        if (!allFieldsNotEmpty) {
            return false
        }

        // Verificar si el email es válido
        val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!isValidEmail) {
            showToast("Por favor, introduce un email válido.")
            return false
        }

        // Verificar si las contraseñas son iguales
        val passwordsMatch = password == confirmPassword
        if (!passwordsMatch) {
            showToast("Las contraseñas no coinciden.")
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
            // Listener del CheckBox
            checkBoxPassword.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Mostrar texto de las contraseñas
                    registerPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    registerConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    // Ocultar texto de las contraseñas
                    registerPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    registerConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            btnContinuar.setOnClickListener {
                join()
            }
        }
    }

    private fun join() {
        with(binding) {
            val check = validateFields()
            if (check) {
                lifecycleScope.launch{
                    val user = User(
                        null,
                        registerUsername.text.toString(),
                        registerPassword.text.toString(),
                        registerEmail.text.toString()
                    )
                    val id =  db?.userDao()?.insert(user)
                    if (id != null) {
                        user.userId = id
                        navigateToEvaluacionSalud(user)
                    }
                }
            }
        }
    }


    private fun navigateToEvaluacionSalud(user: User) {
        val intent = Intent(this, EvaluacionSalud::class.java).apply {
            putExtra("LOGIN_USER", user)
        }
        startActivity(intent)
    }


}
