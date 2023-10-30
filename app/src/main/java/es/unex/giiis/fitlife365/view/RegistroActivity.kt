package es.unex.giiis.fitlife365.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import es.unex.giiis.fitlife365.R

class RegistroActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnContinuar = findViewById<Button>(R.id.idContinuarRegistro)

        btnContinuar.setOnClickListener {
            // Navegar a la pantalla deseada, por ejemplo:
            val intent = Intent(this, MisRutinasActivity::class.java)
            startActivity(intent)
        }
    }
}

