package es.unex.giiis.fitlife365.view.home

import android.app.AlertDialog
import android.content.Intent
import android.content.res.TypedArray
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
import java.util.Locale

class PersonalTrainerFragment : Fragment() {

    private lateinit var timePicker: TimePicker
    private lateinit var datePicker: DatePicker
    private lateinit var buttonDia: Button
    private lateinit var buttonHora: Button
    private lateinit var buttonGuardarDia: Button
    private lateinit var imageView: ImageView
    private lateinit var entrenadoresArray: Array<String>
    private lateinit var telefonosArray: Array<String>
    private lateinit var textViewNombreSeleccionado: TextView
    private lateinit var textView: TextView
    private lateinit var textViewTelefono: TextView
    private lateinit var imagenesEntrenadoresArray: TypedArray
    private var isDatePickerVisible = false
    private lateinit var buttonConsejos: Button


    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var selectedDay: Int = 0
    private var selectedHour: Int = 0
    private var selectedMinute: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal_trainer, container, false)

        Toast.makeText(
            requireContext(),
            "Haz clic en la foto del entrenador para ver la lista de entrenadores",
            Toast.LENGTH_LONG
        ).show()

        timePicker = view.findViewById(R.id.timePicker)
        datePicker = view.findViewById(R.id.datePicker)
        buttonDia = view.findViewById(R.id.button6)
        buttonHora = view.findViewById(R.id.button7)
        buttonGuardarDia = view.findViewById(R.id.buttonGuardarDia)
        imageView = view.findViewById(R.id.imageView8)
        textViewNombreSeleccionado = view.findViewById(R.id.textViewNombreSeleccionado)
        textViewTelefono = view.findViewById(R.id.textViewTelefono)
        textView = view.findViewById(R.id.textView)
        buttonConsejos = view.findViewById(R.id.buttonConsejos)


        entrenadoresArray = resources.getStringArray(R.array.entrenadores_personales)
        telefonosArray = resources.getStringArray(R.array.telefonos_entrenadores)
        imagenesEntrenadoresArray = resources.obtainTypedArray(R.array.imagenes_entrenadores)

        buttonConsejos.setOnClickListener {
            // Abre la actividad de Consejos aquí
            val intent = Intent(requireContext(), ConsejosActivity::class.java)
            startActivity(intent)
        }


        // Configura el evento para mostrar la lista de entrenadores
        imageView.setOnClickListener {
            showEntrenadoresList()
        }

        // Configura el evento de selección de hora
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            // Almacena la hora y los minutos seleccionados
            selectedHour = hourOfDay
            selectedMinute = minute

            // Oculta el TimePicker y muestra los elementos necesarios
            timePicker.visibility = View.GONE
            showTrainerDetails()

            // Desplaza el foco de la vista hacia otro elemento, por ejemplo, el botón de día
            buttonDia.requestFocus()
        }

        // Configura el evento de selección de fecha
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            // Almacena el año, mes y día seleccionados
            selectedYear = year
            selectedMonth = monthOfYear
            selectedDay = dayOfMonth

            datePicker.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            textViewNombreSeleccionado.visibility = View.VISIBLE
            textViewTelefono.visibility = View.VISIBLE
            textView.visibility = View.VISIBLE

            datePicker.translationY = 0f

            // Desplaza el foco de la vista hacia otro elemento, por ejemplo, el botón de guardar día
            buttonDia.requestFocus()

            if (isDatePickerVisible) {
                Toast.makeText(requireContext(), "Para visualizar el día, vuelva a pulsar en el botón día", Toast.LENGTH_SHORT).show()
                isDatePickerVisible = false
            }
        }

        // Configura el evento para mostrar el TimePicker
        buttonHora.setOnClickListener {
            hideTrainerDetails()
            timePicker.visibility = View.VISIBLE
            datePicker.visibility = View.GONE

            val yOffset = 50 // Cambia este valor según tus necesidades
            // Mueve el TimePicker hacia abajo para que sea más visible
            timePicker.translationY = yOffset.toFloat()

            // Oculta el botón para guardar día
            // buttonGuardarDia.visibility = View.GONE
        }

        // Configura el evento para mostrar el DatePicker
        buttonDia.setOnClickListener {
            hideTrainerDetails()
            datePicker.visibility = View.VISIBLE
            timePicker.visibility = View.GONE

            val yOffset = 70 // Cambia este valor según tus necesidades

            // Mueve el DatePicker hacia abajo para que sea más visible
            datePicker.translationY = yOffset.toFloat()

            // Oculta el botón para guardar día
            isDatePickerVisible = true
        }

        buttonGuardarDia.setOnClickListener {
            timePicker.visibility = View.GONE
            showTrainerDetails()
        }


        // Configura el evento para realizar alguna acción al contactar al entrenador personal
        return view
    }

    private fun hideTrainerDetails() {
        imageView.visibility = View.GONE
        textViewNombreSeleccionado.visibility = View.GONE
        textViewTelefono.visibility = View.GONE
        textView.visibility = View.GONE // Oculta el mensaje "ENTRENADOR PERSONAL"
    }

    private fun showTrainerDetails() {
        imageView.visibility = View.VISIBLE
        textViewNombreSeleccionado.visibility = View.VISIBLE
        textViewTelefono.visibility = View.VISIBLE
        textView.visibility = View.VISIBLE // Muestra el mensaje "ENTRENADOR PERSONAL"

        // Muestra la hora seleccionada en un TextView o realiza otra acción según tus necesidades
        val formattedTime =
            String.format(Locale.getDefault(), "Hora Seleccionada: %02d:%02d", selectedHour, selectedMinute)
        Toast.makeText(
            requireContext(),
            "Para modificar la hora, seleccione el numero a modificar. Seleccione AM/PM",
            Toast.LENGTH_LONG
        ).show()
        textView.text = formattedTime
        textView.visibility = View.VISIBLE
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

            imageView.setImageResource(imagenesEntrenadoresArray.getResourceId(which, -1))

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

    // Libera este recurso
    override fun onDestroyView() {
        super.onDestroyView()
        imagenesEntrenadoresArray.recycle()
    }

}
