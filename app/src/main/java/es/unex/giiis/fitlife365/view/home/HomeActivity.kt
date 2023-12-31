package es.unex.giiis.fitlife365.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.EditarPerfilFragment
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.databinding.ActivityHomeBinding
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.SettingsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var usernameText: TextView
    private lateinit var imageViewEvS: ImageView
    private lateinit var usernameTextEvS: TextView
    private lateinit var binding: ActivityHomeBinding
    private lateinit var crearRutinaFragment: CrearRutinaFragment
    private lateinit var misRutinasFragment: MisRutinasFragment
    private lateinit var personalTrainerFragment: PersonalTrainerFragment
    private lateinit var editarPerfilFragment: EditarPerfilFragment


    companion object {
        const val LOGIN_USER = "LOGIN_USER"

        fun start(context: Context, user: User) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(LOGIN_USER, user)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar3)
        usernameText = findViewById(R.id.usernameText)
        imageViewEvS = findViewById(R.id.imageViewEvS)
        usernameTextEvS = findViewById(R.id.usernameTextEvS)

        val user = intent.getSerializableExtra(LOGIN_USER) as User
        usernameText.text = user.name

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            applyFont(window.decorView, selectedFont)
        }

        setUpUI(user)
        setUpListeners()
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_containerHome, fragment)
            commit()
        }

    private fun setUpUI(user: User) {
        crearRutinaFragment = CrearRutinaFragment()
        crearRutinaFragment.setUser(intent.getSerializableExtra(LOGIN_USER) as User)
        misRutinasFragment = MisRutinasFragment.newInstance(intent.getSerializableExtra(LOGIN_USER) as User)
        personalTrainerFragment = PersonalTrainerFragment()
        editarPerfilFragment = EditarPerfilFragment.newInstance(user)
        setCurrentFragment(misRutinasFragment)
    }

    private fun setUpListeners() {
        with(binding){
            usernameText.setOnClickListener {
                navigateToSettings()
            }
            imageViewEvS.setOnClickListener {
                navigateToEvaluacionSalud()
            }
            usernameTextEvS.setOnClickListener {
                navigateToEvaluacionSalud()
            }
            bottomNavigation.setOnItemSelectedListener{
                when(it.itemId){
                    R.id.nav_create_routine -> setCurrentFragment(crearRutinaFragment)
                    R.id.nav_myroutines -> setCurrentFragment(misRutinasFragment)
                    R.id.nav_personaltrainer -> setCurrentFragment(personalTrainerFragment)
                    R.id.nav_editar_perfil -> setCurrentFragment(editarPerfilFragment)
                }
                true
            }
        }
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    public fun navigateToEvaluacionSalud() {
        val user = intent.getSerializableExtra(LOGIN_USER) as? User
        val intent = Intent(this, EvaluacionSaludActivity::class.java).apply {
            // Pasa el usuario como parte de los datos del intent
            putExtra("LOGIN_USER", user)
        }
        startActivity(intent)
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
}