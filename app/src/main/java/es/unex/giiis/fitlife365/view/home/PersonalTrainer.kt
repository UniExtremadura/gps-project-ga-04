import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.unex.giiis.fitlife365.R
import kotlinx.android.synthetic.main.fragment_personal_trainer.*
import java.util.Calendar

class PersonalTrainerFragment : Fragment() {
    private var selectedDay: String = ""
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.personal_trainer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar OnClickListener para el botón de día
        button6.setOnClickListener {
            showDatePicker()
        }

        // Configurar OnClickListener para el botón de hora
        button7.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Al seleccionar una fecha, actualiza la variable selectedDay
                selectedDay = "$dayOfMonth/${monthOfYear + 1}/$year"
                // Actualiza la etiqueta de día para mostrar la fecha seleccionada
                textViewDay.text = selectedDay
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Al seleccionar una hora, actualiza la variable selectedTime
                selectedTime = "$hourOfDay:$minute"
                // Actualiza la etiqueta de hora para mostrar la hora seleccionada
                textViewTime.text = selectedTime
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }
}
