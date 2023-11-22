package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.fitlife365.R

class ConsejosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consejos_seguridad)

        Toast.makeText(
            this,
            "Seleccione el tema del que quiere recibir consejos",
            Toast.LENGTH_LONG
        ).show()

        // Mensaje de texto
        val mensajeTexto: TextView = findViewById(R.id.mensajeTexto)
        mensajeTexto.text = "Consejos de seguridad"

        // Spinner con opciones
        val spinnerTemas: Spinner = findViewById(R.id.spinnerTemas)
        val temas = arrayOf("Calentamiento", "Estiramiento", "Técnica Adecuada", "Límites de Intensidad")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, temas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTemas.adapter = adapter
    }
}
