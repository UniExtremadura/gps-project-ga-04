package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import es.unex.giiis.fitlife365.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CrearRutinaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CrearRutinaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit  var btnAceptar: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_rutina, container, false)
        val diasEntrenamiento = listOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
        val tipoMusculo = listOf("abdominals", "abductors", "adductors", "biceps", "calves", "chest", "forearms", "glutes", "hamstrings", "lats", "lower_back", "middle_back", "neck", "quadriceps", "traps", "triceps")
        val experiencia = listOf("Principiante", "Intermedio", "Avanzado")

        // Configurar el Spinner para los días de entrenamiento
        val spinnerDiasEntrenamiento: Spinner = view.findViewById(R.id.spinnerDiasEntrenamiento)
        val adapterDiasEntrenamiento = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, diasEntrenamiento)
        adapterDiasEntrenamiento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDiasEntrenamiento.adapter = adapterDiasEntrenamiento

        // Configurar el Spinner para los tipos de músculos
        val spinnerMaterial: Spinner = view.findViewById(R.id.spinnerMaterial)
        val adapterTipoMusculo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipoMusculo)
        adapterTipoMusculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMaterial.adapter = adapterTipoMusculo

        // Configurar el Spinner para la experiencia
        val spinnerExperiencia: Spinner = view.findViewById(R.id.spinnerExperiencia)
        val adapterExperiencia = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, experiencia)
        adapterExperiencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerExperiencia.adapter = adapterExperiencia

        // Inicializa el botón
        btnAceptar = view.findViewById(R.id.btnAceptar)

        // Establece el OnClickListener
        btnAceptar.setOnClickListener {
            val misRutinasFragment = MisRutinasFragment()

            // Realiza la transacción del fragmento
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_containerHome, misRutinasFragment)
                addToBackStack(null) // Esto agrega la transacción a la pila de retroceso, permitiendo retroceder al fragmento anterior
                commit()
            }
        }

        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CrearRutinaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CrearRutinaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}