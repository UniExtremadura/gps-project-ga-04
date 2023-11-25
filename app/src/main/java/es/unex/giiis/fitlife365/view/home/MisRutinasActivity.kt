package es.unex.giiis.fitlife365.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.databinding.ActivityMyroutinesBinding
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.SettingsActivity

class MisRutinasActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var usernameText: TextView
    private lateinit var binding: ActivityMyroutinesBinding
    private lateinit var crearRutinaFragment: CrearRutinaFragment
    private lateinit var buttonContinuar: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var imageView4: ImageView
    private lateinit var imageView5: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView

    companion object {
        const val LOGIN_USER = "LOGIN_USER"

        fun start(context: Context, user: User) {
            val intent = Intent(context, MisRutinasActivity::class.java)
            intent.putExtra(LOGIN_USER, user)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar3)
        usernameText = findViewById(R.id.usernameText)

        val user = intent.getSerializableExtra(LOGIN_USER) as User
        usernameText.text = user.name

        buttonContinuar = findViewById(R.id.buttonContinuar)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        imageView4 = findViewById(R.id.imageView4)
        imageView5 = findViewById(R.id.imageView5)
        imageView6 = findViewById(R.id.imageView6)
        imageView7 = findViewById(R.id.imageView7)

        // Asigna OnClickListener a las imágenes de papelera para eliminar la rutina correspondiente
        imageView7.setOnClickListener { eliminarRutina(buttonContinuar) }
        imageView5.setOnClickListener { eliminarRutina(button2) }
        imageView4.setOnClickListener { eliminarRutina(button3) }
        imageView6.setOnClickListener { eliminarRutina(button4) }


        setUpUI()
        setUpListeners()
    }

    private fun eliminarRutina(button: Button) {
        // Oculta el botón
        button.visibility = View.GONE
        // Luego, elimina la rutina de tu lista de rutinas
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
    }

    fun setUpUI() {
        crearRutinaFragment = CrearRutinaFragment()
    }

    fun setUpListeners() {
        with (binding) {
            usernameText.setOnClickListener {
                navigateToSettings()
            }

        }
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}
