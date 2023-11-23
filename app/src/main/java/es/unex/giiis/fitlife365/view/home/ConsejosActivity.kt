package es.unex.giiis.fitlife365.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.SettingsActivity


class ConsejosActivity : AppCompatActivity() {

    private lateinit var spinnerTemas: Spinner
    private lateinit var botonAgregarConsejo: Button
    private lateinit var botonVolver: Button
    private lateinit var mensajeTexto: TextView
    private lateinit var consejosText: TextView

    private var consejosMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    private var indiceConsejoMostrado: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consejos_seguridad)

        // Inicializa tus vistas
        spinnerTemas = findViewById(R.id.spinnerTemas)
        botonAgregarConsejo = findViewById(R.id.botonAgregarConsejo)
        mensajeTexto = findViewById(R.id.mensajeTexto)
        consejosText = findViewById(R.id.consejosText)
        botonVolver = findViewById(R.id.botonVolver)

        // Configura el spinner con tus temas
        val temas = arrayOf("Calentamiento", "Estiramiento", "Técnica adecuada", "Límites de intensidad")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, temas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTemas.adapter = adapter

        // Configura el evento del botón de agregar consejo
        botonAgregarConsejo.setOnClickListener {
            agregarConsejo()
        }

        // Configura el listener para el Spinner
        spinnerTemas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val temaSeleccionado = temas[position]
                // Aquí puedes realizar cualquier acción adicional si es necesario
                Log.d("ConsejosActivity", "Tema seleccionado: $temaSeleccionado")
                mostrarConsejos(temaSeleccionado) // Asegúrate de llamar a esta función al cambiar el tema
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Puedes manejar la falta de selección aquí si es necesario
            }
        }

        botonVolver.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun agregarConsejo() {
        val temaSeleccionado = spinnerTemas.selectedItem.toString()

        val listaConsejos = consejosMap.getOrPut(temaSeleccionado, { mutableListOf() })

        // Verifica si la lista de consejos no está vacía
        if (listaConsejos.isEmpty()) {
            // Si la lista está vacía, muestra un mensaje y no hace nada más
            Toast.makeText(this, "No hay consejos para este tema: $temaSeleccionado", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica si hay más consejos por mostrar
        if (indiceConsejoMostrado < listaConsejos.size) {
            // Muestra el siguiente consejo
            val consejoActual = listaConsejos[indiceConsejoMostrado]
            consejosText.append("$consejoActual\n")
            consejosText.invalidate()

            // Incrementa el índice del último consejo mostrado
            indiceConsejoMostrado++
        } else {
            // Si se han mostrado todos los consejos, muestra un mensaje
            Toast.makeText(this, "No hay más consejos para este tema: $temaSeleccionado", Toast.LENGTH_SHORT).show()

            // No reinicies el índice ni limpies el texto actual
        }

        // Agrega logs para verificar el flujo del programa
        Log.d("ConsejosActivity", "Agregar Consejo: $temaSeleccionado")
        Log.d("ConsejosActivity", "Consejos Map: $consejosMap")
    }



    private fun obtenerConsejoPredeterminado(tema: String): List<String> {
        // Define tus consejos predeterminados aquí
        return when (tema) {
            "Calentamiento" -> listOf(
                "Realiza calentamiento antes de empezar tu rutina.",
                "Incluye ejercicios cardiovasculares en tu calentamiento.",
                "Estira tus músculos principales durante el calentamiento.",
                "Aumenta gradualmente la intensidad del calentamiento.",
                "Escucha a tu cuerpo y ajusta el calentamiento según sea necesario."
            )
            "Estiramiento" -> listOf(
                "Realiza estiramientos para mejorar la flexibilidad.",
                "Incluye estiramientos dinámicos en tu rutina de estiramiento.",
                "Mantén cada estiramiento durante al menos 15-30 segundos.",
                "No rebotes al realizar estiramientos, mantén movimientos suaves.",
                "Realiza estiramientos después de cada sesión de entrenamiento."
            )
            "Técnica adecuada" -> listOf(
                "Asegúrate de mantener una técnica adecuada para prevenir lesiones.",
                "Consulta con un entrenador para corregir tu técnica si es necesario.",
                "Realiza movimientos controlados y evita movimientos bruscos.",
                "Utiliza espejos para verificar y corregir tu forma.",
                "Incrementa la carga o intensidad solo cuando tengas una técnica sólida."
            )
            "Límites de intensidad" -> listOf(
                "No sobrepases tus límites de intensidad, escucha a tu cuerpo.",
                "Utiliza la escala de esfuerzo percibido para monitorizar la intensidad.",
                "Descansa adecuadamente entre las series para recuperarte.",
                "Adapta la intensidad según tu nivel de forma física actual.",
                "Consulta con un profesional de la salud antes de aumentar la intensidad significativamente."
            )
            else -> emptyList()
        }
    }


    private fun mostrarConsejos(tema: String) {
        Log.d("ConsejosActivity", "Mostrar Consejos para el tema: $tema")

        val listaConsejos = consejosMap[tema] ?: emptyList()

        if (listaConsejos.isEmpty()) {
            // Si la lista está vacía, agrega consejos predeterminados
            val nuevosConsejos = obtenerConsejoPredeterminado(tema)
            consejosMap[tema] = nuevosConsejos.toMutableList()  // Convierte la lista a mutable
        }

        if (indiceConsejoMostrado < listaConsejos.size) {
            // Muestra el siguiente consejo
            val consejoActual = listaConsejos[indiceConsejoMostrado]
            consejosText.append("$consejoActual\n")
            consejosText.invalidate()

            // Incrementa el índice del último consejo mostrado
            indiceConsejoMostrado++
        } else {
            // Si la lista está vacía, establece el texto en blanco
            consejosText.text = ""
            consejosText.invalidate()

            // Restablece el índice del último consejo mostrado
            indiceConsejoMostrado = 0
        }
    }
}
