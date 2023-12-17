package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.utils.FontUtils
import es.unex.giiis.fitlife365.view.viewmodels.CrearRutinaViewModel
import es.unex.giiis.fitlife365.view.viewmodels.HomeViewModel

class CrearRutinaFragment : Fragment() {
    private val viewModel: CrearRutinaViewModel by viewModels { CrearRutinaViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit  var btnAceptar: View
    private lateinit var currentUser : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_rutina, container, false)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont =
            sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(requireContext(), view, selectedFont)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.currentUser = user
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

        setUpListeners(spinnerDiasEntrenamiento, spinnerExperiencia)
    }

    private fun setUpListeners(spinnerDiasEntrenamiento : Spinner, spinnerExperiencia : Spinner){
        btnAceptar.setOnClickListener {
            val diasEntrenamiento = spinnerDiasEntrenamiento.selectedItem.toString()
            viewModel.user = currentUser
            viewModel.view = requireView()
            viewModel.diasEntrenamiento = diasEntrenamiento
            val routine = viewModel.crearRutina()

            val difficulty = spinnerExperiencia.selectedItem.toString()
            val listaEjerciciosFragment = ListaEjerciciosFragment.newInstance(currentUser, routine, difficulty)

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_containerHome, listaEjerciciosFragment)
                addToBackStack(null) // Agrega la transacción a la pila de retroceso
                commit()
            }
        }
    }

    fun setUser (user: User){
        currentUser = user
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}