package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import es.unex.giiis.fitlife365.R

class PersonalTrainer : Fragment() {

    private lateinit var timePicker: TimePicker
    private lateinit var datePicker: DatePicker
    private lateinit var buttonDia: Button
    private lateinit var buttonHora: Button
    private lateinit var buttonGuardarDia: Button
    private lateinit var buttonContactar: Button

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
        buttonContactar = view.findViewById(R.id.button8)

        // Configura el evento de selección de hora
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            // Puedes almacenar la hora y el minuto seleccionados o realizar alguna acción
        }

        // Configura el evento de selección de fecha
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            // Puedes almacenar el año, mes y día seleccionados
            selectedYear = year
            selectedMonth = monthOfYear
            selectedDay = dayOfMonth
        }

        // Configura el evento para mostrar el TimePicker
        buttonHora.setOnClickListener {
            timePicker.visibility = View.VISIBLE
            datePicker.visibility = View.GONE
        }

        // Configura el evento para mostrar el DatePicker
        buttonDia.setOnClickListener {
            datePicker.visibility = View.VISIBLE
            timePicker.visibility = View.GONE
        }

        // Configura el evento para guardar el día seleccionado
        buttonGuardarDia.setOnClickListener {
            // Puedes realizar alguna acción con el día seleccionado (selectedYear, selectedMonth, selectedDay)
        }

        // Configura el evento para realizar alguna acción al contactar al entrenador personal
        buttonContactar.setOnClickListener {
            // Aquí puedes realizar alguna acción al hacer clic en "Contactar"
        }

        return view
    }
}
