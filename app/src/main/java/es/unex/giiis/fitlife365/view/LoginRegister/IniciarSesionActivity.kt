package es.unex.giiis.fitlife365.view.LoginRegister

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.view.rutinas.MisRutinasActivity

class IniciarSesionActivity : Activity() {

    private lateinit var btnIniciarSesion: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var textViewRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnIniciarSesion = findViewById(R.id.buttonContinuar)
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        textViewRegister = findViewById(R.id.textView3)

        // Aquí puedes manejar la lógica de la actividad de inicio de sesión
        // Por ejemplo, agregar un Listener al botón de inicio de sesión
        btnIniciarSesion.setOnClickListener {
            if (!validateFields()) {
                showToast("Por favor, completa todos los campos correctamente.")
                return@setOnClickListener
            }

            // Realizar lógica de autenticación aquí (puedes simular una autenticación exitosa)
            val username = etUsername.text.toString()
            val intent = Intent(this, MisRutinasActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        // Agregar un Listener al TextView para redirigir al usuario a la actividad de registro
        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
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
}
