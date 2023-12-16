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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.api.getNetworkService
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.databinding.FragmentCrearRutinaBinding
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.utils.FontUtils
import kotlinx.coroutines.launch

class CrearRutinaFragment : Fragment() {
    private val viewModel: CrearRutinaViewModel by viewModels { CrearRutinaViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding : FragmentCrearRutinaBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrearRutinaBinding.inflate(inflater, container, false)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont =
            sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(requireContext(), binding.root, selectedFont)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.currentUser = user
        }
        setUpSpinners()
        setUpListeners()
    }

    private fun setUpSpinners(){
        val diasEntrenamiento = listOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
        val experiencia = listOf("Principiante", "Intermedio", "Avanzado")

        // Configurar el Spinner para los días de entrenamiento
        val spinnerDiasEntrenamiento: Spinner = requireView().findViewById(R.id.spinnerDiasEntrenamiento)
        val adapterDiasEntrenamiento = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, diasEntrenamiento)
        adapterDiasEntrenamiento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDiasEntrenamiento.adapter = adapterDiasEntrenamiento

        // Configurar el Spinner para la experiencia
        val spinnerExperiencia: Spinner = requireView().findViewById(R.id.spinnerExperiencia)
        val adapterExperiencia = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, experiencia)
        adapterExperiencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerExperiencia.adapter = adapterExperiencia
    }

    private fun setUpListeners(){
        val btnAceptar = view?.findViewById<Button>(R.id.btnAceptar)
        val spinnerDiasEntrenamiento: Spinner = requireView().findViewById(R.id.spinnerDiasEntrenamiento)
        val spinnerExperiencia: Spinner = requireView().findViewById(R.id.spinnerExperiencia)

        btnAceptar!!.setOnClickListener {
            val diasEntrenamiento = spinnerDiasEntrenamiento.selectedItem.toString()

            val routine = viewModel.crearRutina(requireView(), diasEntrenamiento)

            val difficulty = spinnerExperiencia.selectedItem.toString()
            val listaEjerciciosFragment = ListaEjerciciosFragment.newInstance(viewModel.currentUser, routine, difficulty)

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_containerHome, listaEjerciciosFragment)
                addToBackStack(null) // Agrega la transacción a la pila de retroceso
                commit()
            }
        }
    }
}