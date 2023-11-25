package es.unex.giiis.fitlife365.view.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.databinding.ActivityHomeBinding
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.SettingsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var crearRutinaFragment: CrearRutinaFragment
    private lateinit var misRutinasFragment: MisRutinasFragment

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

        setUpUI()
        setUpListeners()
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_containerHome, fragment)
            commit()
    }

    private fun setUpUI() {
        crearRutinaFragment = CrearRutinaFragment()
        crearRutinaFragment.setUser(intent.getSerializableExtra(LOGIN_USER) as User)
        misRutinasFragment = MisRutinasFragment.newInstance(intent.getSerializableExtra(LOGIN_USER) as User)

        setCurrentFragment(misRutinasFragment)
    }
    private fun setUpListeners() {
        with(binding){
            usernameText.setOnClickListener {
                navigateToSettings()
            }
            bottomNavigation.setOnItemSelectedListener{
                when(it.itemId){
                    es.unex.giiis.fitlife365.R.id.nav_create_routine -> setCurrentFragment(crearRutinaFragment)
                    es.unex.giiis.fitlife365.R.id.nav_myroutines -> setCurrentFragment(misRutinasFragment)
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