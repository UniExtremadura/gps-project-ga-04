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

    private lateinit var imageView: ImageView
    private lateinit var entrenadoresArray: Array<String>
    private lateinit var telefonosArray: Array<String>
    private lateinit var textViewNombreSeleccionado: TextView
    private lateinit var textView: TextView
    private lateinit var textViewTelefono: TextView
    private lateinit var imagenesEntrenadoresArray: TypedArray

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


        imageView = view.findViewById(R.id.imageView8)
        textViewNombreSeleccionado = view.findViewById(R.id.textViewNombreSeleccionado)
        textViewTelefono = view.findViewById(R.id.textViewTelefono)
        textView = view.findViewById(R.id.textView)


        entrenadoresArray = resources.getStringArray(R.array.entrenadores_personales)
        telefonosArray = resources.getStringArray(R.array.telefonos_entrenadores)
        imagenesEntrenadoresArray = resources.obtainTypedArray(R.array.imagenes_entrenadores)


        // Configura el evento para mostrar la lista de entrenadores
        imageView.setOnClickListener {
            showEntrenadoresList()
        }

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
