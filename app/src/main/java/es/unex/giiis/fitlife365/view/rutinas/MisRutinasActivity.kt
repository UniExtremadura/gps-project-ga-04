package es.unex.giiis.fitlife365.view.rutinas

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.view.SettingsActivity
import es.unex.giiis.fitlife365.view.SettingsFragment

class MisRutinasActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var usernameText: TextView
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_routines)

        toolbar = findViewById(R.id.toolbar3)
        drawerLayout = findViewById(R.id.drawerLayout)
        usernameText = findViewById(R.id.usernameText)
        navigationView = findViewById(R.id.navigationView)

        setSupportActionBar(toolbar) // Establecer la toolbar como ActionBar

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        usernameText.text = intent.getStringExtra("username")

        usernameText.setOnClickListener {
            // Navegar a la pantalla de ajustes
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Aquí puedes configurar las acciones para los elementos del Navigation Drawer
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // Define acciones al hacer clic en los elementos del menú
                // Ir a la pantalla de ajustes
                R.id.menu_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}
