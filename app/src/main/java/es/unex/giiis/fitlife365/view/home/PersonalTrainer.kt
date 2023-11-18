package es.unex.giiis.fitlife365.view.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.unex.giiis.fitlife365.R

class PersonalTrainer : Fragment() {

    private lateinit var timePicker: TimePicker
    private lateinit var datePicker: DatePicker
    private lateinit var buttonDia: Button
    private lateinit var buttonHora: Button
    private lateinit var buttonGuardarDia: Button
    private lateinit var imageView: ImageView
    private lateinit var entrenadoresArray: Array<String>
    private lateinit var telefonosArray: Array<String>
    private lateinit var textViewNombreSeleccionado: TextView
    private lateinit var textViewTelefono: TextView




    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var selectedDay: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.personal_trainer, container, false)

        timePicker = view.findViewById(R.id.timePicker)
        datePicker = view.findViewById(R.id.datePicker)
        buttonDia = view.findViewById(R.id.button6)
        buttonHora = view.findViewById(R.id.button7)
        buttonGuardarDia = view.findViewById(R.id.buttonGuardarDia)
        imageView = view.findViewById(R.id.imageView8)
        textViewNombreSeleccionado = view.findViewById(R.id.textViewNombreSeleccionado)
        textViewTelefono = view.findViewById(R.id.textViewTelefono)


        entrenadoresArray = resources.getStringArray(R.array.entrenadores_personales)
        telefonosArray = resources.getStringArray(R.array.telefonos_entrenadores)

        // Configura el evento para mostrar la lista de entrenadores
        imageView.setOnClickListener {
            showEntrenadoresList()
        }
        // Configura el evento de selección de hora
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            // Puedes almacenar la hora y el minuto seleccionados o realizar alguna acción
            datePicker.visibility = View.GONE
            imageView.visibility = View.VISIBLE

            datePicker.translationY = 0f

            // Desplazar el foco de la vista hacia otro elemento, por ejemplo, el botón de guardar día
            buttonGuardarDia.requestFocus()
        }

        // Configura el evento de selección de fecha
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            // Puedes almacenar el año, mes y día seleccionados
            selectedYear = year
            selectedMonth = monthOfYear
            selectedDay = dayOfMonth

            datePicker.visibility = View.GONE
            imageView.visibility = View.VISIBLE

            datePicker.translationY = 0f

            // Desplazar el foco de la vista hacia otro elemento, por ejemplo, el botón de guardar día
            buttonGuardarDia.requestFocus()

        }

        // Configura el evento para mostrar el TimePicker
        buttonHora.setOnClickListener {
            timePicker.visibility = View.VISIBLE
            datePicker.visibility = View.GONE
            imageView.visibility = View.GONE // Oculta la foto al mostrar el DatePicker

            val yOffset = 200 // Cambia este valor según tus necesidades

            // Mueve el DatePicker hacia abajo para que sea más visible
            datePicker.translationY = yOffset.toFloat()
        }

        // Configura el evento para mostrar el DatePicker
        buttonDia.setOnClickListener {
            datePicker.visibility = View.VISIBLE
            timePicker.visibility = View.GONE
            imageView.visibility = View.GONE // Oculta la foto al mostrar el DatePicker

            val yOffset = 200 // Cambia este valor según tus necesidades

            // Mueve el DatePicker hacia abajo para que sea más visible
            datePicker.translationY = yOffset.toFloat()

        }

        // Configura el evento para guardar el día seleccionado
        buttonGuardarDia.setOnClickListener {
            datePicker.visibility = View.GONE
            timePicker.visibility = View.GONE
            imageView.visibility = View.VISIBLE // Muestra la foto al ocultar el DatePicker

            // Puedes realizar alguna acción con el día seleccionado (selectedYear, selectedMonth, selectedDay)
        }

        // Configura el evento para realizar alguna acción al contactar al entrenador personal
        // Configura el evento para realizar alguna acción al contactar al entrenador personal

        return view
    }
    private fun showEntrenadoresList() {
        // Crea un cuadro de diálogo para mostrar la lista de entrenadores
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Selecciona un entrenador")

        // Convierte el array de entrenadores a una lista
        val entrenadoresList = entrenadoresArray.toList()

        // Configura el adaptador para el cuadro de diálogo
        builder.setItems(telefonosArray) { _, which ->
            // Maneja la selección del entrenador
            val selectedTelefono = telefonosArray[which]
            val nombre = selectedTelefono.substringBefore(':')
            val telefono = selectedTelefono.substringAfter(':')

            Toast.makeText(requireContext(), "Seleccionaste: $nombre", Toast.LENGTH_SHORT).show()

            textViewNombreSeleccionado.text = "Nombre Seleccionado: $nombre"
            textViewNombreSeleccionado.visibility = View.VISIBLE

            // Almacena el teléfono seleccionado
            textViewTelefono.text = "Tlf de contacto: $telefono"
            textViewTelefono.visibility = View.VISIBLE
        }

        // Muestra el cuadro de diálogo
        builder.show()
    }


    // Método para obtener el teléfono por nombre completo
    // Método para obtener el teléfono por nombre completo
    private fun getTelefonoByNombre(nombre: String): String {
        // Busca el ítem que contiene el nombre en el array de entrenadores
        val item = telefonosArray.find { it.startsWith("$nombre:") }

        // Si se encuentra el ítem, extrae el número de teléfono
        return item?.substringAfter(':') ?: ""
    }

}


