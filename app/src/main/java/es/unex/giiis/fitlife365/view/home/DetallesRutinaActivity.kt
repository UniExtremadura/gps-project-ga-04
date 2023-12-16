package es.unex.giiis.fitlife365.view.home

import DetallesRutinaViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.databinding.ActivityDetallesRutinaBinding
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.SettingsActivity
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.api.getNetworkService
import es.unex.giiis.fitlife365.data.Repository
import es.unex.giiis.fitlife365.utils.FontUtils


class DetallesRutinaActivity : AppCompatActivity() {

    private lateinit var nombreRutina: TextView
    private lateinit var pesoObjetivo: TextView
    private lateinit var diasSemana: TextView
    private lateinit var detallesSeries: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var usernameText: TextView
    private lateinit var eliminarButton: Button
    private lateinit var volverHome: Button
    private lateinit var binding : ActivityDetallesRutinaBinding
    private lateinit var rutina : Routine
    private lateinit var recyclerViewEjercicios: RecyclerView
    private lateinit var ejerciciosAdapter: EjerciciosAdapter
    private val viewModel: DetallesRutinaViewModel by viewModels { DetallesRutinaViewModel.Factory }
    private lateinit var currentUser : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(this, window.decorView, selectedFont)
        }


        currentUser = intent.getSerializableExtra("LOGIN_USER") as User
        viewModel.rutina = intent.getSerializableExtra("RUTINA") as Routine

        setUpUI()
        setUpListeners()
    }

    private fun setUpRecyclerView() {
        recyclerViewEjercicios = findViewById(R.id.recyclerViewEjercicios)
        ejerciciosAdapter = EjerciciosAdapter() // Puedes crear un adaptador personalizado para tus ejercicios
        recyclerViewEjercicios.adapter = ejerciciosAdapter
        recyclerViewEjercicios.layoutManager = LinearLayoutManager(this)
    }
    private fun setUpListeners() {
        with(binding) {
            usernameText.setOnClickListener {
                navigateToSettings()
            }
            eliminarButton.setOnClickListener {
                mostrarDialogoConfirmacion()
            }
            volverHome.setOnClickListener {
                viewModel.updateCompletionStatusForAllExercises()
                navigateToHomeActivity()
            }
        }
    }

    private fun mostrarDialogoConfirmacion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar la rutina?")

        // Botón Aceptar
        builder.setPositiveButton("Aceptar") { _, _ ->
            viewModel.eliminarRutina()
            navigateToHomeActivity()
        }

        // Botón Cancelar
        builder.setNegativeButton("Cancelar") { _, _ ->
            // No hacer nada, simplemente cerrar el diálogo
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun setUpUI() {
        nombreRutina = findViewById(R.id.nombreRutina)
        pesoObjetivo = findViewById(R.id.pesoObjetivo)
        diasSemana = findViewById(R.id.diasSemana)
        usernameText = findViewById(R.id.usernameText)
        detallesSeries = findViewById(R.id.seriesRepes)
        eliminarButton = findViewById(R.id.btnEliminarRutina)
        volverHome = findViewById(R.id.btnVolverHome)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        usernameText.text = currentUser.name
        nombreRutina.text = viewModel.rutina.name ?: "No hay nombre en esta rutina"
        pesoObjetivo.text = (viewModel.rutina.pesoObjetivo?.toString() + " kg") ?: "No hay peso objetivo en esta rutina"
        diasSemana.text = viewModel.rutina.diasEntrenamiento ?: "No hay días de entrenamiento en esta rutina"
        detallesSeries.text = "3 x 10 - 90s"
        viewModel.adapter = ejerciciosAdapter
        viewModel.mostrarDetallesDeTodosLosEjercicios()

    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHomeActivity() {
        HomeActivity.start(this, currentUser)
    }

}
