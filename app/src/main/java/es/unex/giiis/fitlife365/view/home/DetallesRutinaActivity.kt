package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
    private lateinit var user : User
    private lateinit var recyclerViewEjercicios: RecyclerView
    private lateinit var ejerciciosAdapter: EjerciciosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rutina = intent.getSerializableExtra("RUTINA") as Routine
        user = intent.getSerializableExtra("USER") as User

        recyclerViewEjercicios = findViewById(R.id.recyclerViewEjercicios)
        ejerciciosAdapter = EjerciciosAdapter() // Puedes crear un adaptador personalizado para tus ejercicios
        recyclerViewEjercicios.adapter = ejerciciosAdapter
        recyclerViewEjercicios.layoutManager = LinearLayoutManager(this)

        setUpUI()
        setUpListeners()
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
                updateCompletionStatusForAllExercises()
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
            eliminarRutina()
            navigateToHomeActivity()
        }

        // Botón Cancelar
        builder.setNegativeButton("Cancelar") { _, _ ->
            // No hacer nada, simplemente cerrar el diálogo
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun updateCompletionStatusForAllExercises() {
        val database = FitLife365Database.getInstance(this)
        val exerciseModelDao = database?.exerciseModelDao()
        lifecycleScope.launch { val updatedExerciseList = ejerciciosAdapter.obtenerEjercicios().map {
            exerciseModelDao?.updateExercise(it)
            }
        }
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
        usernameText.text = user.name
        nombreRutina.text = rutina.name ?: "No hay nombre en esta rutina"
        pesoObjetivo.text = (rutina.pesoObjetivo?.toString() + " kg") ?: "No hay peso objetivo en esta rutina"
        diasSemana.text = rutina.diasEntrenamiento ?: "No hay días de entrenamiento en esta rutina"
        detallesSeries.text = "3 x 10 - 90s"
        mostrarDetallesDeTodosLosEjercicios(rutina.ejercicios)
    }

    private fun mostrarDetallesDeTodosLosEjercicios(listaEjerciciosIds: String?) {
        if (listaEjerciciosIds.isNullOrBlank()) {
            return
        }

        val listaIds = listaEjerciciosIds.split(",").map { it.trim() }

        lifecycleScope.launch {
            val ejerciciosList = mutableListOf<ExerciseModel>()

            for (exerciseId in listaIds) {
                val exerciseModelDao = FitLife365Database.getInstance(this@DetallesRutinaActivity)?.exerciseModelDao()
                val exercise = exerciseModelDao?.getExerciseById(exerciseId.toLong())

                exercise?.let { ejerciciosList.add(it) }
            }

            ejerciciosAdapter.updateData(ejerciciosList)
        }
    }



    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun eliminarRutina() {
        val database = FitLife365Database.getInstance(this)
        val rutinaDao = database?.routineDao()

        lifecycleScope.launch {
            rutinaDao?.deleteRoutine(rutina.routineId)
        }
    }



    private fun navigateToHomeActivity() {
        HomeActivity.start(this, user)
    }

}
