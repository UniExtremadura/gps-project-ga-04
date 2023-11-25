package es.unex.giiis.fitlife365.view.LoginRegister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.databinding.ActivityRegisterBinding
import es.unex.giiis.fitlife365.model.User
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

        db = FitLife365Database.getInstance(applicationContext)!!

        btnContinuar = findViewById(R.id.idContinuarRegistro)
        registerUsername = findViewById(R.id.registerUsername)
        registerPassword = findViewById(R.id.registerPassword)
        registerEmail = findViewById(R.id.registerEmail)
        registerConfirmPassword = findViewById(R.id.registerConfirmPassword)
        checkBoxPassword = findViewById(R.id.checkBoxPassword)



        setUpListeners()
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
                        navigateToLogin(user)
                    }
                }
            }
        }
    }


    private fun navigateToLogin(user: User) {
        val intent = Intent(this, IniciarSesionActivity::class.java).apply {
            putExtra("LOGIN_USER", user)
        }
        startActivity(intent)
    }


}
