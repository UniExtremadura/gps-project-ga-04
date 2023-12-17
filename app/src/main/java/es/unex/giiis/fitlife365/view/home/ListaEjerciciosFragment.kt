package es.unex.giiis.fitlife365.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.databinding.FragmentListaEjerciciosBinding
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.utils.FontUtils
import es.unex.giiis.fitlife365.view.adapters.ListaEjerciciosAdapter
import es.unex.giiis.fitlife365.view.viewmodels.HomeViewModel
import es.unex.giiis.fitlife365.view.viewmodels.ListaEjerciciosViewModel


class ListaEjerciciosFragment : Fragment() {
    private val viewModel: ListaEjerciciosViewModel by viewModels { ListaEjerciciosViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _exercise: List<ExerciseModel> = emptyList()
    private lateinit var adapter : ListaEjerciciosAdapter
    private var _binding: FragmentListaEjerciciosBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnGuardar : Button
    private lateinit var btnConfirmar: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaEjerciciosBinding.inflate(inflater, container, false)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(requireContext(), binding.root, selectedFont)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Configurar el RecyclerView
        setUpRecyclerView()
        subscribeUi(adapter)

        btnGuardar = view.findViewById(R.id.btnGuardarEnRutina)
        btnConfirmar = view.findViewById(R.id.btnConfirmarEjercicios)

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        viewModel.rutina = arguments?.getSerializable("rutina") as Routine
        viewModel.dificultad = arguments?.getString("difficulty") ?: "Principiante" // Valor predeterminado si no se encuentra

        viewModel.toast.observe(viewLifecycleOwner){text ->
            text?.let { Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        // Configurar el Spinner para seleccionar el músculo
        val musclesArray = resources.getStringArray(R.array.muscle_array)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, musclesArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMusculo.adapter = spinnerAdapter
        // Configurar el Spinner con OnItemSelectedListener
        binding.spinnerMusculo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Lógica que se ejecuta cuando se selecciona un elemento en el Spinner
                val selectedMuscle = parent?.getItemAtPosition(position).toString()
                viewModel.musculo = selectedMuscle
                viewModel.adapter = adapter
                viewModel.filtrarEjerciciosPorMusculo(selectedMuscle, viewModel.dificultad)
                adapter = viewModel.adapter!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Lógica que se ejecuta cuando no se selecciona ningún elemento en el Spinner
            }
        }
    }

    private fun setUpListeners(){
        btnGuardar.setOnClickListener {
            viewModel.insertarEjerRutina()
        }

        btnConfirmar.setOnClickListener {
            // launchDataLoad { repository.tryUpdateRecentExercicesCache(musculoElegido, difficulty) }
            navigateToHome(viewModel.user!!)
        }
    }

    private fun subscribeUi(adapter: ListaEjerciciosAdapter) {
        viewModel.exercises.observe(viewLifecycleOwner) { result ->
            adapter.updateData(result)
            setUpListeners()
        }
    }

    private fun navigateToHome(user: User) {
        HomeActivity.start(requireContext(), user)
    }

    private fun setUpRecyclerView() {
        adapter = ListaEjerciciosAdapter(_exercise, ::mostrarDetallesDelEjercicio)
        with(binding) {
            rvListaEjercicios.layoutManager = LinearLayoutManager(context)
            rvListaEjercicios.adapter = adapter
        }
    }

    private fun mostrarDetallesDelEjercicio(exercise: ExerciseModel) {
        // Crear un cuadro de diálogo emergente para mostrar detalles del ejercicio
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_detalles_ejercicio)

        // Configurar el contenido del cuadro de diálogo con los detalles del ejercicio
        val tvNombre = dialog.findViewById<TextView>(R.id.tvNombreEjercicio)
        val tvTipo = dialog.findViewById<TextView>(R.id.tvTipoEjercicio)
        val tvMusculo = dialog.findViewById<TextView>(R.id.tvMusculo)
        val tvEquipo = dialog.findViewById<TextView>(R.id.tvEquipment)
        val tvDificultad = dialog.findViewById<TextView>(R.id.tvDifficulty)
        val tvInstrucciones = dialog.findViewById<TextView>(R.id.tvInstructions)

        tvNombre.text = exercise.name
        tvTipo.text = exercise.type
        tvMusculo.text = exercise.muscle
        tvEquipo.text = exercise.equipment
        tvDificultad.text = exercise.difficulty
        tvInstrucciones.text = exercise.instructions

        // Agregar un botón (por ejemplo, ImageView con una 'X') para cerrar el cuadro de diálogo
        val btnCerrar = dialog.findViewById<ImageView>(R.id.btnCerrar)
        btnCerrar.setOnClickListener {
            dialog.dismiss()
        }

        // Mostrar el cuadro de diálogo
        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // En el companion object
    companion object {
        fun newInstance(user: User, rutina : Routine, difficulty : String): ListaEjerciciosFragment {
            val fragment = ListaEjerciciosFragment()
            val args = Bundle()
            args.putSerializable("user", user)
            args.putSerializable("rutina", rutina)
            args.putString("difficulty", difficulty)
            fragment.arguments = args
            return fragment
        }
    }
}