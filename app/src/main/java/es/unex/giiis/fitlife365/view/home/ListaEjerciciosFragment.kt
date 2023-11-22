package es.unex.giiis.fitlife365.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.api.APIError
import es.unex.giiis.fitlife365.api.getNetworkService
import es.unex.giiis.fitlife365.data.toExercise
import es.unex.giiis.fitlife365.databinding.FragmentListaEjerciciosBinding
import es.unex.giiis.fitlife365.model.ExerciseModel
import kotlinx.coroutines.launch


class ListaEjerciciosFragment : Fragment() {
    private var _exercise: List<ExerciseModel> = emptyList()
    private lateinit var adapter : ListaEjerciciosAdapter
    private var _binding: FragmentListaEjerciciosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaEjerciciosBinding.inflate(inflater, container, false)
        return binding.root

    }

    private val muscleMapping = mapOf(
        "Abdominales" to "abdominals",
        "Abductores"  to "abductors",
        "Aductores" to "adductors",
        "Bíceps" to "biceps",
        "Pantorrillas" to "calves",
        "Pecho" to "chest",
        "Antebrazos" to "forearms",
        "Glúteos" to "glutes",
        "Isquiotibiales" to "hamstrings",
        "Dorsales" to "lats",
        "Lumbares" to "lower_back",
        "Espalda" to "middle_back",
        "Cuello" to "neck",
        "Cuadríceps" to "quadriceps",
        "Trapecios" to "traps",
        "Tríceps" to "triceps"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        setUpRecyclerView()

        // Configurar el Spinner para seleccionar el músculo
        val musclesArray = resources.getStringArray(R.array.muscle_array)
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, musclesArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMusculo.adapter = spinnerAdapter

        // Configurar el Spinner con OnItemSelectedListener
        binding.spinnerMusculo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Lógica que se ejecuta cuando se selecciona un elemento en el Spinner
                val selectedMuscle = parent?.getItemAtPosition(position).toString()
                filtrarEjerciciosPorMusculo(selectedMuscle)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Lógica que se ejecuta cuando no se selecciona ningún elemento en el Spinner
            }
        }
    }

    private fun filtrarEjerciciosPorMusculo(muscle: String) {
        lifecycleScope.launch {
            try {
                // Traducir el nombre del músculo al inglés utilizando el mapeo
                val muscleInEnglish = muscleMapping[muscle] ?: muscle
                // Filtrar ejercicios por músculo (usando el nombre en inglés)
                _exercise = getNetworkService().getExercisesByMuscle(muscleInEnglish).toExercise()
                adapter.updateData(_exercise)
            } catch (error: APIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaEjerciciosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
