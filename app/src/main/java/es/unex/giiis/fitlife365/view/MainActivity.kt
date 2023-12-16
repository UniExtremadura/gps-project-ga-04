package es.unex.giiis.fitlife365.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.view.LoginRegister.IniciarSesionActivity
import es.unex.giiis.fitlife365.view.LoginRegister.RegistroActivity
import es.unex.giiis.fitlife365.utils.FontUtils

class MainActivity : AppCompatActivity() {

    private var isRecreating = false
    override fun onCreate(savedInstanceState: Bundle?) {
        // Obtener la preferencia del tema
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePreference = sharedPreferences.getBoolean("theme_preference", false)

        // Aplicar el tema en esta actividad solo si no se está recreando
        if (!isRecreating) {
            if (themePreference) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        // Obtener la fuente seleccionada desde SharedPreferences
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(this, window.decorView, selectedFont)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        setUpListeners()
    }

    private fun setUpListeners() {
        val btnIniciarSesion = findViewById<Button>(R.id.iniciarSesionButton)
        val btnRegistrarse = findViewById<Button>(R.id.registrateButton)

        btnIniciarSesion.setOnClickListener {
            // Navegar a la pantalla de inicio de sesión
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }

        btnRegistrarse.setOnClickListener {
            // Navegar a la pantalla de registro
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}


