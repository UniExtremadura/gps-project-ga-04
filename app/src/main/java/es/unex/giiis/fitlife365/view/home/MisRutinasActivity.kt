package es.unex.giiis.fitlife365.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
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


        setUpUI()
        setUpListeners()
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
            bottomNavigation.setOnItemSelectedListener{
                when(it.itemId){
                    R.id.nav_create_routine -> setCurrentFragment(crearRutinaFragment)
                }
                true
            }
        }
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}
