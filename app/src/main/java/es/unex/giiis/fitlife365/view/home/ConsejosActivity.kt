package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.fitlife365.R


class ConsejosActivity : AppCompatActivity() {

    private lateinit var spinnerTemas: Spinner
    private lateinit var botonAgregarConsejo: Button
    private lateinit var mensajeTexto: TextView
    private lateinit var consejosText: TextView

    private var consejosList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consejos)

        // Inicializa tus vistas
        spinnerTemas = findViewById(R.id.spinnerTemas)
        botonAgregarConsejo = findViewById(R.id.botonAgregarConsejo)
        mensajeTexto = findViewById(R.id.mensajeTexto)
        consejosText = findViewById(R.id.consejosText)


        // Configura el spinner con tus temas
        val temas = arrayOf("Calentamiento", "Estiramiento", "Técnica adecuada", "Límites de intensidad")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, temas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTemas.adapter = adapter

        // Configura el evento del botón de agregar consejo
        botonAgregarConsejo.setOnClickListener {
            agregarConsejo()
        }
    }

    private fun agregarConsejo() {
        val temaSeleccionado = spinnerTemas.selectedItem.toString()

        // Verifica si ya hay 5 consejos para el tema seleccionado
        if (consejosList.filter { it == temaSeleccionado }.size < 5) {
            // Agrega un nuevo consejo
            val nuevoConsejo = obtenerConsejoPredeterminado(temaSeleccionado)
            consejosList.add(nuevoConsejo)

            // Muestra los consejos
            mostrarConsejos(temaSeleccionado)
        } else {
            // Muestra el mensaje de límite alcanzado
            mensajeTexto.text = "No hay más consejos disponibles para el tema $temaSeleccionado"
        }

        // Agrega logs para verificar el flujo del programa
        Log.d("ConsejosActivity", "Agregar Consejo: $temaSeleccionado")
        Log.d("ConsejosActivity", "Consejos List: $consejosList")
    }

    private fun obtenerConsejoPredeterminado(tema: String): String {
        // Define tus consejos predeterminados aquí
        return when (tema) {
            "Calentamiento" -> "Realiza calentamiento antes de empezar tu rutina."
            "Estiramiento" -> "Realiza estiramientos para mejorar la flexibilidad."
            "Técnica Adecuada" -> "Asegúrate de mantener una técnica adecuada para prevenir lesiones."
            "Límites de Intensidad" -> "No sobrepases tus límites de intensidad, escucha a tu cuerpo."
            else -> ""
        }
    }

    private fun mostrarConsejos(tema: String) {
        val consejos = obtenerConsejoPredeterminado(tema)
        consejosText.text = consejos
        consejosText.invalidate()
    }



}