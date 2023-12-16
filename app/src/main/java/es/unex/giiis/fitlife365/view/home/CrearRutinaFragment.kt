package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.api.getNetworkService
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.utils.FontUtils
import kotlinx.coroutines.launch

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
    private val viewModel: CrearRutinaViewModel by viewModels { CrearRutinaViewModel.Factory }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit  var btnAceptar: View
    private lateinit var nombreRutina : EditText
    private lateinit var pesoObjetivoRutina : EditText
    private lateinit var currentUser : User
    private lateinit var repository: Repository
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
        //inicia la variable repository
        val appContainer = (this.activity?.application as FitLife365Application).appContainer
        repository = appContainer.repository

        val view = inflater.inflate(R.layout.fragment_crear_rutina, container, false)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(requireContext(), view, selectedFont)
        }

        val diasEntrenamiento = listOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
        val experiencia = listOf("Principiante", "Intermedio", "Avanzado")

        // Configurar el Spinner para los días de entrenamiento
        val spinnerDiasEntrenamiento: Spinner = view.findViewById(R.id.spinnerDiasEntrenamiento)
        val adapterDiasEntrenamiento = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, diasEntrenamiento)
        adapterDiasEntrenamiento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDiasEntrenamiento.adapter = adapterDiasEntrenamiento

        // Configurar el Spinner para la experiencia
        val spinnerExperiencia: Spinner = view.findViewById(R.id.spinnerExperiencia)
        val adapterExperiencia = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, experiencia)
        adapterExperiencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerExperiencia.adapter = adapterExperiencia

        // Inicializa el botón
        btnAceptar = view.findViewById(R.id.btnAceptar)

        // Establece el OnClickListener
        //view.findViewById<Button>(R.id.btnAceptar).setOnClickListener(viewModel.btnAceptarClickListener)
        btnAceptar.setOnClickListener {
            val diasEntrenamiento = spinnerDiasEntrenamiento.selectedItem.toString()

            viewModel.setUser(currentUser)
            val routine = viewModel.crearRutina(view, diasEntrenamiento)

            val difficulty = spinnerExperiencia.selectedItem.toString()
            val listaEjerciciosFragment = ListaEjerciciosFragment.newInstance(currentUser, routine, difficulty)

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_containerHome, listaEjerciciosFragment)
                addToBackStack(null) // Agrega la transacción a la pila de retroceso
                commit()
            }
        }

        return view
    }
    fun setUser (user: User){
        currentUser = user
    }

    override fun onDestroyView() {
        super.onDestroyView()
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