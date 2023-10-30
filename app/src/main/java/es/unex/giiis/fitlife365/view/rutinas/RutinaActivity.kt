package es.unex.giiis.fitlife365.view.rutinas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import es.unex.giiis.fitlife365.R

class RutinaActivity : Activity() {

    private lateinit var btnCrearRutina: Button
    private lateinit var btnMisRutinas: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.routine_screen)

        btnCrearRutina = findViewById(R.id.buttonCrearRutina)
        btnMisRutinas = findViewById(R.id.buttonMisRutinas)

        btnCrearRutina.setOnClickListener {
            // Navegar a la pantalla de inicio de sesión
            val intent = Intent(this, CrearRutinaActivity::class.java)
            startActivity(intent)
        }

        btnMisRutinas.setOnClickListener {
            // Navegar a la pantalla de registro
            val intent = Intent(this, MisRutinasActivity::class.java)
            startActivity(intent)
        }
    }


}