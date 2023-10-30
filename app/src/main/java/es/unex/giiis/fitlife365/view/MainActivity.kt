package es.unex.giiis.fitlife365.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.view.LoginRegister.IniciarSesionActivity
import es.unex.giiis.fitlife365.view.LoginRegister.RegistroActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        val btnIniciarSesion = findViewById<Button>(R.id.iniciarSesionButton)
        val btnRegistrarse = findViewById<Button>(R.id.registrateButton)

        btnIniciarSesion.setOnClickListener {
            // Navegar a la pantalla de inicio de sesión
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }

        btnRegistrarse.setOnClickListener {
            // Navegar a la pantalla de registro
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}