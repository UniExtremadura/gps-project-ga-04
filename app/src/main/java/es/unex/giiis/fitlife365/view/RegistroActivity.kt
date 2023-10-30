package es.unex.giiis.fitlife365.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import es.unex.giiis.fitlife365.R

class RegistroActivity : Activity() {

    val btnContinuar = findViewById<Button>(R.id.idContinuarRegistro)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnContinuar.setOnClickListener {
            // Navegar a la pantalla de inicio de sesión
            val intent = Intent(this, MisRutinasActivity::class.java)
            startActivity(intent)
        }
    }


}
