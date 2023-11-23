package es.unex.giiis.fitlife365.view.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.api.APIError
import es.unex.giiis.fitlife365.api.getNetworkService
import es.unex.giiis.fitlife365.data.toExercise
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.databinding.FragmentListaEjerciciosBinding
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import kotlinx.coroutines.launch


class ListaEjerciciosFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_lista_ejercicios, container, false)
        _binding = FragmentListaEjerciciosBinding.inflate(inflater, container, false)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            applyFont(view, selectedFont)
        }

        return binding.root
    }

    private fun applyFont(view: View, fontName: String) {
        when (view) {
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    applyFont(view.getChildAt(i), fontName)
                }
            }
            is TextView -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is EditText -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente a la barra de edición de texto
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is Button -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente al botón
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
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

    private val difficultyMapping = mapOf(
        "Principiante" to "beginner",
        "Intermedio" to "intermediate",
        "Avanzado" to "expert"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Configurar el RecyclerView
        setUpRecyclerView()
        val database = FitLife365Database.getInstance(requireContext())
        val difficulty = arguments?.getString("difficulty") ?: "Principiante" // Valor predeterminado si no se encuentra

        btnGuardar = view.findViewById(R.id.btnGuardarEnRutina)
        btnConfirmar = view.findViewById(R.id.btnConfirmarEjercicios)

        val user = arguments?.getSerializable("user") as User
        val rutina = arguments?.getSerializable("rutina") as Routine

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
                filtrarEjerciciosPorMusculo(selectedMuscle, difficulty )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Lógica que se ejecuta cuando no se selecciona ningún elemento en el Spinner
            }
        }

        btnGuardar.setOnClickListener {
            val listaEjerciciosSeleccionados = adapter.getSelectedExercises()
            val exerciseModelDao = database?.exerciseModelDao()
            val rutinaDao = database?.routineDao()

            lifecycleScope.launch {
                // Obtener la rutina actual
                val routineEntity = rutinaDao?.getRoutineById(rutina.routineId)

                // Obtener la cadena de ejercicios anterior
                val cadenaEjerciciosAnterior = routineEntity?.ejercicios ?: ""

                // Insertar cada ejercicio seleccionado en ExerciseModelDao
                for (ejercicio in listaEjerciciosSeleccionados) {
                    val id = exerciseModelDao?.insert(ejercicio)
                    ejercicio.exerciseId = id
                    Log.d("Ejercicio insertado: ", ejercicio.exerciseId.toString())
                    exerciseModelDao?.addRoutineExercise(id, rutina.routineId)
                    Log.d("Ejercicio añadido a rutina: ", rutina.routineId.toString())
                }

                // Concatenar los ejercicios anteriores con los nuevos
                val cadenaEjerciciosNueva = if (cadenaEjerciciosAnterior.isNotEmpty()) {
                    cadenaEjerciciosAnterior + "," + listaEjerciciosSeleccionados.joinToString(",") { it.exerciseId.toString() }
                } else {
                    listaEjerciciosSeleccionados.joinToString(",") { it.exerciseId.toString() }
                }

                // Actualizar la cadena de ejercicios en la rutina
                rutinaDao?.updateRoutine(rutina.routineId, cadenaEjerciciosNueva)
                Log.d("Ejercicios añadidos a rutina: ", cadenaEjerciciosNueva)
            }
        }

        btnConfirmar.setOnClickListener {
            navigateToHome(user)
        }
    }

    private fun navigateToHome(user: User) {
        HomeActivity.start(requireContext(), user)
    }

    private fun filtrarEjerciciosPorMusculo(muscle: String, difficulty: String) {
        lifecycleScope.launch {
            try {
                // Traducir el nombre del músculo al inglés utilizando el mapeo
                val muscleInEnglish = muscleMapping[muscle] ?: muscle

                // Traducir la dificultad al inglés utilizando el mapeo
                val difficultyInEnglish = difficultyMapping[difficulty] ?: difficulty

                // Filtrar ejercicios por músculo y dificultad (usando el nombre en inglés)
                _exercise = getNetworkService().getExercisesByMuscleAndDifficulty(muscleInEnglish, difficultyInEnglish).toExercise()
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
