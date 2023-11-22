package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.databinding.ActivityDetallesRutinaBinding
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.SettingsActivity
import kotlinx.coroutines.launch

class DetallesRutinaActivity : AppCompatActivity() {

    private lateinit var nombreRutina: TextView
    private lateinit var pesoObjetivo: TextView
    private lateinit var diasSemana: TextView
    private lateinit var ejercicios: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var usernameText: TextView
    private lateinit var eliminarButton: Button
    private lateinit var volverHome: Button
    private lateinit var binding : ActivityDetallesRutinaBinding
    private lateinit var rutina : Routine
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rutina = intent.getSerializableExtra("RUTINA") as Routine
        user = intent.getSerializableExtra("USER") as User




        setUpUI()
        setUpListeners()
    }


    private fun setUpListeners() {
        with(binding) {
            usernameText.setOnClickListener {
                navigateToSettings()
            }
            eliminarButton.setOnClickListener {
                eliminarRutina()
                navigateToHomeActivity()
            }
            volverHome.setOnClickListener {
                navigateToHomeActivity()
            }
        }
    }

    private fun setUpUI() {
        nombreRutina = findViewById(R.id.nombreRutina)
        pesoObjetivo = findViewById(R.id.pesoObjetivo)
        diasSemana = findViewById(R.id.diasSemana)
        ejercicios = findViewById(R.id.ejercicios)
        usernameText = findViewById(R.id.usernameText)
        eliminarButton = findViewById(R.id.btnEliminarRutina)
        volverHome = findViewById(R.id.btnVolverHome)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        usernameText.text = user.name
        nombreRutina.text = rutina.name ?: "No hay nombre en esta rutina"
        pesoObjetivo.text = rutina.pesoObjetivo?.toString() ?: "No hay peso objetivo en esta rutina"
        diasSemana.text = rutina.diasEntrenamiento ?: "No hay días de entrenamiento en esta rutina"
        ejercicios.text = rutina.ejercicios ?: "No hay ejercicios en esta rutina"
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
